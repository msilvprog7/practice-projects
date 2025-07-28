package main

import (
	"fmt"
	"math"
	"math/cmplx"
	"math/rand"
)

const Pi = 3.14
const (
	Big = 1 << 100
	Small = Big >> 99
)

var (
	ToBe   bool       = false
	MaxInt uint64     = 1 << 64 - 1
	z 		 complex128 = cmplx.Sqrt(-5 + 12i)
)

func add(x, y int) int {
	return x + y
}

func swap(x, y string) (string, string) {
	return y, x
}

func split(sum int) (x, y int) {
	x = sum * 4 / 9
	y = sum - x
	return
}

func needInt(x int) int { return x * 10 + 1 }
func needFloat(x float64) float64 { return x * 0.1 }

func main() {
	fmt.Println("My favorite number is", rand.Intn(10))
	fmt.Println(math.Pi)
	fmt.Println(add(42, 13))
	
	a, b := swap("hello", "world")
	fmt.Println(a, b)

	fmt.Println(split(17))

	var i, j int = 1, 2
	c, python, java := true, false, "no!"
	fmt.Println(i, j, c, python, java)

	fmt.Printf("Type: %T Value: %v\n", ToBe, ToBe)
	fmt.Printf("Type: %T Value: %v\n", MaxInt, MaxInt)
	fmt.Printf("Type: %T Value: %v\n", z, z)

	var i2 int
	var f float64
	var b2 bool
	var s string
	fmt.Printf("%v %v %v %q\n", i2, f, b2, s)

	var x, y int = 3, 4
	var f2 float64 = math.Sqrt(float64(x*x + y*y))
	var z uint = uint(f2)
	fmt.Println(x, y, z)

	v := 3.142
	fmt.Printf("v is of type %T\n", v)

	const World = "世界"
	fmt.Println("Hello", World)
	fmt.Println("Happy", Pi, "Day")

	const Truth = true
	fmt.Println("Go rules?", Truth)

	fmt.Println(needInt(Small))
	fmt.Println(needFloat(Small))
	fmt.Println(needFloat(Big))
}