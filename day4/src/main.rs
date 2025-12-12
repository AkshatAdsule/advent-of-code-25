use std::{
    io::{self, BufRead},
    usize::MAX,
};

fn main() {
    let stdin = io::stdin();
    let stdin_lock = stdin.lock();

    let mut grid: Vec<Vec<char>> = Vec::new();

    for line_result in stdin_lock.lines() {
        match line_result {
            Ok(line) => {
                grid.push(line.chars().collect());
            }
            Err(error) => {
                eprintln!("Could not parse line! {error}");
                break;
            }
        }
    }

    let part1_results = solve_part1(&grid).unwrap();
    let part2_results = solve_part2(&grid).unwrap();

    println!("Part 1 results: {part1_results}");
    println!("Part 2 results: {part2_results}");
}

fn is_valid(x: isize, y: isize, rows: isize, cols: isize) -> bool {
    x >= 0 && x < rows && y >= 0 && y < cols
}

#[allow(dead_code)]
fn print_grid(grid: &Vec<Vec<char>>) {
    for r in grid {
        for c in r {
            print!("{c}");
        }
        println!();
    }
    println!()
}

fn get_num_surrounding_rolls(r: usize, c: usize, grid: &Vec<Vec<char>>) -> Option<usize> {
    if grid[r][c] != '@' {
        return None;
    }

    let row = grid.get(r)?;
    let mut surrounding_count = 0;

    let r = r as isize;
    let c = c as isize;

    for x in (r - 1)..=(r + 1) {
        for y in (c - 1)..=(c + 1) {
            if (x == r && y == c) || !is_valid(x, y, grid.len() as isize, row.len() as isize) {
                continue;
            }
            if *grid.get(x as usize)?.get(y as usize)? == '@' {
                surrounding_count += 1
            }
        }
    }

    Some(surrounding_count)
}

fn solve_part1(grid: &Vec<Vec<char>>) -> Option<usize> {
    let mut count = 0;

    for i in 0..grid.len() {
        for j in 0..grid[i].len() {
            let surrounding_count = match get_num_surrounding_rolls(i, j, grid) {
                Some(n) => n,
                None => MAX,
            };
            if surrounding_count < 4 {
                count += 1;
            }
        }
    }

    Some(count)
}

fn solve_part2(grid: &Vec<Vec<char>>) -> Option<usize> {
    let mut grid = grid.clone();

    let mut count = 0;

    loop {
        let n_changed = mutate_grid(&mut grid).expect("Mutate failed!");
        if n_changed == 0 {
            return Some(count);
        }
        count += n_changed;
    }
}

fn mutate_grid(grid: &mut Vec<Vec<char>>) -> Option<usize> {
    let mut accessible_rolls: Vec<(usize, usize)> = Vec::new();

    for i in 0..grid.len() {
        let row = grid.get(i)?;
        for j in 0..row.len() {
            let surrounding_count = match get_num_surrounding_rolls(i, j, grid) {
                Some(n) => n,
                None => MAX,
            };
            if surrounding_count < 4 {
                accessible_rolls.push((i, j));
            }
        }
    }

    let num_removed = accessible_rolls.len();

    for (r, c) in accessible_rolls {
        grid[r][c] = '.';
    }

    return Some(num_removed);
}
