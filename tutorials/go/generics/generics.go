package main

import (
	"fmt"
	"strings"
)

type List[T any] struct {
	next *List[T]
	val T
}

func Index[T comparable](s []T, x T) int {
	for i, v := range s {
		if v == x {
			return i
		}
	}

	return -1
}

func (list *List[T]) Size() int {
	count := 0

	for list != nil {
		count++
		list = list.next
	}

	return count
}

func ToList[T any](sl []T) *List[T] {
	if len(sl) == 0 {
		return nil
	}

	head := &List[T]{val: sl[0]}
	curr := head

	for i := 1; i < len(sl); i++ {
		curr.next = &List[T]{val: sl[i]}
		curr = curr.next
	}

	return head
}

func (list *List[T]) ToArray() []T {
	if list == nil {
		return nil
	}
	
	size := list.Size()
	result := make([]T, size)
	curr := list
	
	for i := 0; i < size && curr != nil; i++ {
		result[i] = curr.val
		curr = curr.next
	}

	return result
}

func (list *List[T]) String() string {
	if list == nil {
		return "nil"
	}

	arr := list.ToArray()
	strArr := make([]string, len(arr))
	for i, v := range arr {
		strArr[i] = fmt.Sprintf("%v", v)
	}
	return strings.Join(strArr, " -> ")
}

func main() {
	si := []int {10, 20, 15, -10}
	fmt.Println(Index(si, 15))

	ss := []string{"foo", "bar", "baz"}
	fmt.Println(Index(ss, "hello"))

	ll := ToList([]int { 1, 2, 3, 4, 5 })
	fmt.Println(ll.Size())
	fmt.Println(ll.String())
}