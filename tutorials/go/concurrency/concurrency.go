package main

import (
	"fmt"
	"sync"
	"time"

	"golang.org/x/tour/tree"
)

type SafeCounter struct {
	mu sync.Mutex
	v  map[string]int
}

type fakeFetcher map[string]*fakeResult

type fakeResult struct {
	body string
	urls []string
}

type visited struct {
	waitGroup sync.WaitGroup
	mutex sync.Mutex
	set map[string]struct{}
}

func say(s string) {
	for i := 0; i < 5; i++ {
		time.Sleep(100 * time.Millisecond)
		fmt.Println(s)
	}
}

func sum(s []int, c chan int) {
	sum := 0
	for _, v := range s {
		sum += v
	}
	c <- sum // send sum to c
}

func fibonacci(n int, c chan int) {
	x, y := 0, 1

	for range n {
		c <- x
		x, y = y, x + y
	}

	close(c)
}

func fibonacciWithQuit(c, quit chan int) {
	x, y := 0, 1
	for {
		select {
		case c <- x:
			x, y = y, x + y
		case <-quit:
			fmt.Println("quit")
			return
		}
	}
}

func defaultSelection() {
	start := time.Now()
	tick := time.Tick(100 * time.Millisecond)
	boom := time.After(500 * time.Millisecond)
	elapsed := func() time.Duration {
		return time.Since(start).Round(time.Millisecond)
	}

	for {
		select {
		case <- tick:
			fmt.Printf("[%6s] tick.\n", elapsed())
		case <- boom:
			fmt.Printf("[%6s] BOOM!\n", elapsed())
			return
		default:
			fmt.Printf("[%6s]     .\n", elapsed())
			time.Sleep(50 * time.Millisecond)
		}
	}
}

// Walk walks the tree t sending all values
// from the tree to the channel ch.
func Walk(t *tree.Tree, ch chan int) {
	if t == nil {
		return
	}

	Walk(t.Left, ch)
	ch <- t.Value
	Walk(t.Right, ch)
}

// Same determines whether the trees
// t1 and t2 contain the same values.
func Same(t1, t2 *tree.Tree) bool {
	c1 := make(chan int, 10)
	c2 := make(chan int, 10)

	go Walk(t1, c1)
	go Walk(t2, c2)

	for range 10 {
		v1, v2 := <- c1, <- c2
		if v1 != v2 {
			return false
		}
	}

	return true
}

func (c *SafeCounter) Inc(key string) {
	c.mu.Lock()
	c.v[key]++
	c.mu.Unlock()
}

func (c *SafeCounter) Value(key string) int {
	c.mu.Lock()
	defer c.mu.Unlock()
	return c.v[key]
}

type Fetcher interface {
	// Fetch returns the body of URL and
	// a slice of URLs found on that page.
	Fetch(url string) (body string, urls []string, err error)
}

// Crawl uses fetcher to recursively crawl
// pages starting with url, to a maximum of depth.
func Crawl(url string, depth int, fetcher Fetcher, fetched *visited) {
	// Don't fetch the same URL twice.
	defer fetched.waitGroup.Done()
	fetched.mutex.Lock()

	_, found := fetched.set[url]
	if found {
		fmt.Printf("skip: %s\n", url)
		fetched.mutex.Unlock()
		return
	}

	fetched.set[url] = emptyStruct
	fetched.mutex.Unlock()

	// This implementation doesn't do either:
	if depth <= 0 {
		return
	}
	body, urls, err := fetcher.Fetch(url)
	if err != nil {
		fmt.Println(err)
		return
	}
	fmt.Printf("found: %s %q\n", url, body)
	for _, u := range urls {
	  // Fetch URLs in parallel.
		fetched.waitGroup.Add(1)
		go Crawl(u, depth-1, fetcher, fetched)
	}
}


func (f fakeFetcher) Fetch(url string) (string, []string, error) {
	if res, ok := f[url]; ok {
		return res.body, res.urls, nil
	}
	return "", nil, fmt.Errorf("not found: %s", url)
}

// fetcher is a populated fakeFetcher.
var fetcher = fakeFetcher{
	"https://golang.org/": &fakeResult{
		"The Go Programming Language",
		[]string{
			"https://golang.org/pkg/",
			"https://golang.org/cmd/",
		},
	},
	"https://golang.org/pkg/": &fakeResult{
		"Packages",
		[]string{
			"https://golang.org/",
			"https://golang.org/cmd/",
			"https://golang.org/pkg/fmt/",
			"https://golang.org/pkg/os/",
		},
	},
	"https://golang.org/pkg/fmt/": &fakeResult{
		"Package fmt",
		[]string{
			"https://golang.org/",
			"https://golang.org/pkg/",
		},
	},
	"https://golang.org/pkg/os/": &fakeResult{
		"Package os",
		[]string{
			"https://golang.org/",
			"https://golang.org/pkg/",
		},
	},
}
var fetched = visited{
	waitGroup: sync.WaitGroup{},
	mutex: sync.Mutex{},
	set: make(map[string]struct{}),
}
var emptyStruct = struct{}{}

func main() {
	// go say("world")
	// say("hello")

	s := []int{7, 2, 8, -9, 4, 0}
	c := make(chan int)
	go sum(s[:len(s) / 2], c)
	go sum(s[len(s) / 2:], c)
	x, y := <- c, <- c // receive from c

	fmt.Println(x, y, x + y)

	// buffered channels
	ch := make(chan int, 2)
	ch <- 1
	ch <- 2
	fmt.Println(<-ch)
	fmt.Println(<-ch)

	// range and close
	c = make(chan int, 10)
	go fibonacci(cap(c), c)
	for i := range c {
		fmt.Println(i)
	}

	// select
	c = make(chan int)
	quit := make(chan int)
	go func() {
		for range 10 {
			fmt.Println(<-c)
		}
		quit <- 0
	}()
  fibonacciWithQuit(c, quit)

	// default selection
	defaultSelection()

  // exercise: equivalent binary trees
	ch = make(chan int, 10)
	go Walk(tree.New(1), ch)
	for range 10 {
		fmt.Printf("%d ", <-ch)
	}
	fmt.Printf("\n")

	fmt.Printf("%v.\n", Same(tree.New(1), tree.New(1)))
	fmt.Printf("%v.\n", Same(tree.New(1), tree.New(2)))
	
	// sync.Mutex
	counter := SafeCounter{v: make(map[string]int)}
	for i := 0; i < 1000; i++ {
		go counter.Inc("somekey")
	}

	time.Sleep(time.Second)
	fmt.Println(counter.Value("somekey"))

	// exercise: Web Crawler
	fmt.Println("started: crawl")
	fetched.waitGroup.Add(1)
	go Crawl("https://golang.org/", 4, fetcher, &fetched)
	fetched.waitGroup.Wait()
	fmt.Println("completed: crawl")
}