package main

import (
	"fmt"
	"math"
	"strings"

	"golang.org/x/tour/pic"
	"golang.org/x/tour/wc"
)

type Vertex struct {
	X int
	Y int
}

type Vertex2 struct {
	Lat, Long float64
}

var (
	v1 = Vertex{1, 2}
	v2 = Vertex{X: 1}
	v3 = Vertex{}
	p3 = &Vertex{1, 2}
)

func printSlice(s []int) {
	fmt.Printf("len=%d cap=%d %v\n", len(s), cap(s), s)
}

func printSlice2(s string, x []int) {
	fmt.Printf("%s len=%d cap=%d %v\n", s, len(x), cap(x), x)
}

func appendToSlice() {
	var s []int
	printSlice(s)

	s = append(s, 0)
	printSlice(s)

	s = append(s, 1)
	printSlice(s)

	s = append(s, 2, 3, 4)
	printSlice(s)
}

func tryRange() {
	var pow = []int {1, 2, 4, 8, 16, 32, 64, 128}

	for i, v := range pow {
		fmt.Printf("2**%d = % d\n", i, v)
	}

	pow2 := make([]int, 10)
	for i := range pow2 {
		pow2[i] = 1 << uint(i) // == 2 ** i
	}

	for _, v := range pow2 {
		fmt.Printf("%d\n", v)
	}
}

func Pic(dx, dy int) [][]uint8 {
	picture := make([][]uint8, dx)

	for row := range picture {
		picture[row] = make([]uint8, dy)
	}

	for row := range picture {
		for col := range picture[row] {
			picture[row][col] = uint8((row + col) / 2)
		}
	}

	return picture
}

func maps() {
	m := make(map[string]Vertex2)
	m["Bell Labs"] = Vertex2{40.68433, -74.39967}
	fmt.Println(m["Bell Labs"])

	m2 := map[string]Vertex2{
		"Bell Labs": Vertex2{
			40.68433, -74.39967,
		},
		"Google": Vertex2{
			37.42202, -122.08408,
		},
	}
	fmt.Println(m2)

	m3 := map[string]Vertex2{
		"Bell Labs": {40.68433, -74.39967},
		"Google": {37.42202, -122.08408},
	}
	fmt.Println(m3)

	m4 := make(map[string]int)
	m4["Answer"] = 42
	fmt.Println("The value:", m4["Answer"])
	m4["Answer"] = 48
	fmt.Println("The value:", m4["Answer"])
	delete(m4, "Answer")
	fmt.Println("The value:", m4["Answer"])
	v, ok := m4["Answer"]
	fmt.Println("The value:", v, "Present?", ok)
}

func WordCount(s string) map[string]int {
	wordCount := make(map[string]int)
	words := strings.Fields(s)

	for _, word := range words {
		wordCount[word]++
	}

	return wordCount
}

func compute(fn func(float64, float64) float64) float64 {
	return fn(3, 4)
}

func functionValues() {
	hypot := func(x, y float64) float64 {
		return math.Sqrt(x*x + y*y)
	}
	fmt.Println(hypot(5, 12))

	fmt.Println(compute(hypot))
	fmt.Println(compute(math.Pow))
}

func adder() func(int) int {
	sum := 0
	return func(x int) int {
		sum += x
		return sum
	}
}

func functionClosures() {
	pos, neg := adder(), adder()
	for i := 0; i < 10; i++ {
		fmt.Println(pos(i), neg(-2*i))
	}
}

func fibonacci() func() int {
	first := 0
	second := 1
	return func() int {
		result := first

		// update
		third := first + second
		first = second
		second = third

		return result
	}
}

func testFibonacci() {
	f := fibonacci()
	for i := 0; i < 10; i++ {
		fmt.Println(f())
	}
}

func main() {
	i, j := 42, 2701

	p := &i
	fmt.Println(*p)
	*p = 21
	fmt.Println(i)

	p = &j
	*p = *p / 37
	fmt.Println(j)

	fmt.Println(Vertex{1, 2})

	v := Vertex{1, 2}
	fmt.Println(v.X)

	p2 := &v
	p2.X = 1e9
	fmt.Println(v)

	fmt.Println(v1, p2, v2, v3)

	var a [2]string
	a[0] = "Hello"
	a[1] = "World"
	fmt.Println(a[0], a[1])
	fmt.Println(a)

	primes := [6]int{2, 3, 5, 7, 11, 13}
	fmt.Println(primes)

	var s []int = primes[1:4]
	fmt.Println(s)

	names := [4]string{
		"John",
		"Paul",
		"George",
		"Ringo",
	}
	fmt.Println(names)

	a2 := names[0:2]
	b := names[1:3]
	fmt.Println(a2, b)

	b[0] = "XXX"
	fmt.Println(a2, b)
	fmt.Println(names)

	q := []int{2, 3, 5, 7, 11, 13}
	fmt.Println(q)

	r := []bool{true, false, true, true, false, true}
	fmt.Println(r)

	s2 := []struct {
		i int
		b bool
	}{
		{2, true},
		{3, false},
		{5, true},
		{7, true},
		{11, false},
		{13, true},
	}
	fmt.Println(s2)

	s3 := []int{2, 3, 5, 7, 11, 13}
	printSlice(s3)
	
	s3 = s3[1:4]
	fmt.Println(s3)

	s3 = s3[:2]
	fmt.Println(s3)

	s3 = s3[1:]
	fmt.Println(s3)

	s4 := []int{2, 3, 5, 7, 11, 13}
	printSlice(s4)

	s4 = s4[:0]
	printSlice(s4)

	s4 = s4[:4]
	printSlice(s4)

	s4 = s4[2:]
	printSlice(s4)

	var s5 []int
	fmt.Println(s5, len(s5), cap(s5))
	if s5 == nil {
		fmt.Println("nil!")
	}

	a3 := make([]int, 5)
	printSlice2("a", a3)

	b2 := make([]int, 0, 5)
	printSlice2("b", b2)

	c := b2[:2]
	printSlice2("c", c)

	d := c[2:5]
	printSlice2("d", d)

	board := [][]string {
		[]string{"_", "_", "_"},
		[]string{"_", "_", "_"},
		[]string{"_", "_", "_"},
	}

	board[0][0] = "X"
	board[2][2] = "O"
	board[1][2] = "X"
	board[1][0] = "O"
	board[0][2] = "X"

	for i := 0; i < len(board); i++ {
		fmt.Printf("%s\n", strings.Join(board[i], " "))
	}

	appendToSlice()
	tryRange()
	pic.Show(Pic)
	maps()
	wc.Test(WordCount)
	functionValues()
	functionClosures()
	testFibonacci()
}