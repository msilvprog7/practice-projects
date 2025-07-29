package main

import (
	"fmt"
	"image"
	"image/color"
	"io"
	"math"
	"os"
	"strconv"
	"strings"
	"time"

	"golang.org/x/tour/pic"
	"golang.org/x/tour/reader"
)

type MyFloat float64

type Vertex struct {
	X, Y float64
}

type Abser interface {
	Abs() float64
}

type I interface {
	M()
}

type T struct {
	S string
}

type F float64

type Person struct {
	Name string
	Age int
}

type IPAddr [4]byte

type MyError struct {
	When time.Time
	What string
}

type ErrNegativeSqrt float64

type MyReader struct{}

type Rot13Reader struct {
	r io.Reader
}

type Image struct{
	width, height int
}

// receiver argument
func (v *Vertex) Abs() float64 {
	return math.Sqrt(v.X * v.X + v.Y * v.Y)
}

func (f MyFloat) Abs() float64 {
	if f < 0 {
		return float64(-f)
	}
	return float64(f)
}

// pointer receivers can modify value and avoids copying value on each method call
func (v *Vertex) Scale(f float64) {
	v.X = v.X * f
	v.Y = v.Y * f
}

func ScaleFunc(v *Vertex, f float64) {
	v.X = v.X * f
	v.Y = v.Y * f
}

func (t *T) M() {
	if t == nil {
		fmt.Println("<nil>")
		return
	}

	fmt.Println(t.S)
}

func (f F) M() {
	fmt.Println(f)
}

func describe(i interface{}) {
	fmt.Printf("(%v, %T)\n", i, i)
}

func typeAssertions() {
	var i interface{} = "hello"

	s := i.(string)
	fmt.Println(s)

	s, ok := i.(string)
	fmt.Println(s, ok)

	f, ok := i.(float64)
	fmt.Println(f, ok)

	// f = i.(float64) // panic
	// fmt.Println(f)
}

func do(i interface{}) {
	switch v := i.(type) {
	case int:
		fmt.Printf("Twice %v is %v\n", v, v*2)
	case string:
		fmt.Printf("%q is %v bytes long\n", v, len(v))
	default:
		fmt.Printf("I don't know about type %T!\n", v)
	}
}

func typeSwitches() {
	do(21)
	do("hello")
	do(true)
}

func (p Person) String() string {
	return fmt.Sprintf("%v (%v years)", p.Name, p.Age)
}

func stringers() {
	a := Person{"Arthur Dent", 42}
	z := Person{"Zaphod Beeblebrox", 9001}
	fmt.Println(a, z)
}

func (ip IPAddr) String() string {
	return fmt.Sprintf("%v.%v.%v.%v", ip[0], ip[1], ip[2], ip[3])
}

func testStringers() {
	hosts := map[string]IPAddr{
		"loopback": {127, 0, 0, 1},
		"googleDNS": {8, 8, 8, 8},
	}
	for name, ip := range hosts {
		fmt.Printf("%v: %v\n", name, ip)
	}
}

func (e *MyError) Error() string {
	return fmt.Sprintf("at %v, %s", e.When, e.What)
}

func run() error {
	return &MyError{time.Now(), "it didn't work"}
}

func errors() {
	if err := run(); err != nil {
		fmt.Println(err)
	}

	i, err2 := strconv.Atoi("42")
	if err2 != nil {
		fmt.Printf("couldn't convert number: %v\n", err2)
	} else {
		fmt.Println("Converted integer:", i)
	}
}

func (e ErrNegativeSqrt) Error() string {
	return fmt.Sprintf("cannot Sqrt negative number: %v", float64(e))
}

func Sqrt(x float64) (float64, error) {
	if x < 0 {
		return 0, ErrNegativeSqrt(x)
	}

	z := 1.0

	for i := 0; i < 10; i++ {
		delta := (z * z - x) / (2 * z)
		z -= delta
		fmt.Println(z)

		if math.Abs(delta) <= 1e-6 {
			break
		}
	}

	return z, nil
}

func testErrors() {
	fmt.Println(Sqrt(2))
	fmt.Println(Sqrt(-2))
}

func readers() {
	r := strings.NewReader("Hello, Reader!")
	b := make([]byte, 8)

	for {
		n, err := r.Read(b)
		fmt.Printf("n = %v err = %v b = %v\n", n, err, b)
		fmt.Printf("b[:n] = %q\n", b[:n])
		if err == io.EOF {
			break
		}
	}
}

func (myReader MyReader) Read(bytes []byte) (int, error) {
	for i := range bytes {
		bytes[i] = 'A'
	}
	return len(bytes), nil
}

func testReaders() {
	reader.Validate(MyReader{})
}

func (rot13Reader Rot13Reader) Read(bytes []byte) (int, error) {
	count, err := rot13Reader.r.Read(bytes)
	if err == io.EOF {
		return count, err
	}

	for i := range count {
		switch {
		case bytes[i] >= 'A' && bytes[i] <= 'Z':
			bytes[i] = 'A' + (bytes[i] - 'A' + 13) % 26
		case bytes[i] >= 'a' && bytes[i] <= 'z':
			bytes[i] = 'a' + (bytes[i] - 'a' + 13) % 26
		}
	}

	return count, nil
}

func testRot13Reader() {
	s := strings.NewReader("Lbh penpxrq gur pbqr!")
	r := Rot13Reader{s}
	io.Copy(os.Stdout, &r)
}

func images() {
	m := image.NewRGBA(image.Rect(0, 0, 100, 100))
	fmt.Println(m.Bounds())
	fmt.Println(m.At(0, 0).RGBA())
}

func (i Image) Bounds() image.Rectangle {
	return image.Rect(0, 0, i.width, i.height)
}

func (i Image) ColorModel() color.Model {
	return color.RGBAModel
}

func (i Image) At(x, y int) color.Color {
	v := uint8((x + y) / 2)
	return color.RGBA{v, v, 255, 255}
}

func testImages() {
	m := Image{200, 300}
	pic.ShowImage(m)
}

func main() {
	v := Vertex{3, 4}
	fmt.Println(v.Abs())

	f := MyFloat(-math.Sqrt2)
	fmt.Println(f.Abs())

	v.Scale(10)
	fmt.Println(v.Abs())

	p := &Vertex{4, 3}
	p.Scale(3)
	ScaleFunc(p, 8)
	fmt.Println(v, p)

	var a Abser
	f = MyFloat(-math.Sqrt2)
	v = Vertex{3, 4}

	a = f
	a = &v
	// a = v // v does not implement interface
	fmt.Println(a.Abs())

	var i I = &T{"hello"}
	i.M()

	i = &T{"Hello"}
	describe(i)
	i.M()

	i = F(math.Pi)
	describe(i)
	i.M()

	var t *T
	i = t
	describe(i)
	i.M()

	// invalid memory address
	// var i2 I
	// describe(i2)
	// i2.M()

	typeAssertions()
	typeSwitches()
	stringers()
	testStringers()
	errors()
	testErrors()
	readers()
	testReaders()
	testRot13Reader()
	images()
	testImages()
}