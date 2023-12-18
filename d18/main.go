package main

import (
	"fmt"
	"os"
	"strings"
	"strconv"
)

type point struct{x, y uint64}

func abs(i uint64) uint64 {
	if i < 0 {
		return -i
	}
	return i
}

func shoelace(points []point) uint64 {
	var sum uint64
	sum = 0
	p0 := points[len(points) - 1]
	for i, p1 := range points {
		if i != len(points) - 1 {
			sum += p1.x * (points[i + 1].y - p0.y)
		} else {
			sum += p1.x * (points[0].y - p0.y)
		}
		p0 = p1
	}
	return abs(sum) / 2
}

func solve(partOne bool) {
	data, err := os.ReadFile("input")
	if err != nil {
		panic(err)
	}
	lines := strings.Split(strings.Trim(string(data), "\n"), "\n")
	var points []point
	var sum uint64
	var x uint64
	var y uint64
	sum = 0
	x = 1
	y = 1
	for _, line := range lines {
		instructions := strings.Split(line, " ")
		d := string(strings.Trim(instructions[2], "(#)")[5:6])
		amount, _ := strconv.ParseUint(strings.Trim(instructions[2], "(#)")[:5], 16, 32)
		if d == "0" {
			d = "R"
		} else if d == "1" {
			d = "D"
		} else if d == "2" {
			d = "L"
		} else {
			d = "U"
		}
		if partOne {
			d = instructions[0]
			amount, _ = strconv.ParseUint(instructions[1], 10, 64)
		}
		sum += amount
		if d == "R" {
			x += amount
		} else if d == "L" {
			x -= amount
		} else if d == "D" {
			y += amount
		} else {
			y -= amount
		}
		points = append(points, point{x, y})
	}

	area := shoelace(points)
	if partOne {
		fmt.Print("Part one: ")
	} else {
		fmt.Print("Part two: ")
	}
	fmt.Println(area + 1 - (sum / 2) + sum)
}

func main() {
	solve(true)
	solve(false)
}
