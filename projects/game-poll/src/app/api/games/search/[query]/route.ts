import { NextRequest, NextResponse } from "next/server";
import { GameSuggestion } from "@/app/types";

let cachedToken: string | null = null;
let tokenExpiry: number = 0;

async function getValidToken() {
  // Check if we have a valid cached token
  if (cachedToken && Date.now() < tokenExpiry) {
    return cachedToken;
  }

  console.log("Getting new token...");
  console.log("Client ID:", process.env.IGDB_CLIENT_ID);
  console.log("Client Secret exists:", !!process.env.IGDB_CLIENT_SECRET);

  // Get new token
  const response = await fetch("https://id.twitch.tv/oauth2/token", {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
    body: new URLSearchParams({
      client_id: process.env.IGDB_CLIENT_ID!,
      client_secret: process.env.IGDB_CLIENT_SECRET!,
      grant_type: "client_credentials",
    }),
  });

  console.log("Token response status:", response.status);
  const tokenData = await response.json();
  console.log("Token response:", tokenData);

  if (!response.ok) {
    throw new Error(`Failed to get token: ${JSON.stringify(tokenData)}`);
  }

  // Cache the token (expires in ~60 days typically)
  cachedToken = tokenData.access_token;
  tokenExpiry = Date.now() + tokenData.expires_in * 1000 - 60000; // Refresh 1 min early

  return cachedToken;
}

export async function GET(
  request: NextRequest,
  { params }: { params: Promise<{ query: string }> }
) {
  try {
    const { query } = await params;
    const { searchParams } = new URL(request.url);
    const top = parseInt(searchParams.get("top") || "10");

    if (!query) {
      return NextResponse.json(
        { error: "Missing 'query' parameter" },
        { status: 400 }
      );
    }

    if (isNaN(top)) {
      return NextResponse.json(
        { error: "Number required for 'top' parameter" },
        { status: 400 }
      );
    }

    const accessToken = await getValidToken();

    const response = await fetch("https://api.igdb.com/v4/search", {
      method: "POST",
      headers: {
        "Client-ID": process.env.IGDB_CLIENT_ID!,
        Authorization: `Bearer ${accessToken}`,
        "Content-Type": "text/plain",
      },
      body: `
        search "${query}";
        fields game.name, game.cover.url;
        where game != null;
        limit ${top};
      `,
    });

    if (!response.ok) {
      throw new Error(`IGDB API error: ${response.status}`);
    }

    const igdbData = await response.json();

    // Simple mapping to GameSuggestion array
    const gameSuggestions: GameSuggestion[] = igdbData
      .filter((result: any) => result.game && result.game.name)
      .map((result: any) => ({
        name: result.game.name,
      }));

    return NextResponse.json(gameSuggestions);
  } catch (error) {
    console.error("IGDB API error:", error);
    return NextResponse.json(
      { error: "Failed to search games" },
      { status: 500 }
    );
  }
}
