use std::{
    char,
    io::{self, BufRead},
};

fn main() -> io::Result<()> {
    let stdin = io::stdin();
    let stdin_lock = stdin.lock();

    let mut lock_part1 = build_lock();
    let mut sum_part1 = 0;

    let mut lock_part2 = build_lock();
    let mut sum_part2 = 0;

    for line in stdin_lock.lines() {
        let line = line?;

        let mut chars = line.chars();
        let dir = chars.nth(0).expect("Invalid format!");
        let amount: isize = str::parse(&line[1..]).expect("Invalid format!");

        sum_part1 += lock_part1.rotate_part1(dir, amount);
        sum_part2 += lock_part2.rotate_part2(dir, amount);
    }

    println!("Part 1: {sum_part1}");
    println!("Part 2: {sum_part2}");

    Ok(())
}

struct Lock {
    val: isize,
}

impl Lock {
    fn rotate_part1(&mut self, dir: char, amount: isize) -> isize {
        // print!("[P1]: Turning {dir} by {amount}");
        let amount = if dir == 'L' { -amount } else { amount };

        self.val += amount;
        self.val = self.val.rem_euclid(100);

        // println!(", new val: {0}", self.val);

        if self.val == 0 { 1 } else { 0 }
    }

    fn rotate_part2(&mut self, dir: char, amount: isize) -> isize {
        let norm = if dir == 'L' {
            if self.val == 0 { 100 } else { self.val }
        } else {
            100 - self.val
        };

        let rem = amount - norm;

        let count = if rem < 0 { 0 } else { 1 + rem / 100 };

        self.rotate_part1(dir, amount);

        count
    }
}

fn build_lock() -> Lock {
    Lock { val: 50 }
}
