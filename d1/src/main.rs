use std::fs;

fn main() {
    let contents = fs::read_to_string("input1").unwrap();
    let lines = contents.lines();
    let mut sum: u64 = 0;
    let mut closure = |l: String| {
        let mut cs: Vec<char> = l.chars().collect();
        let mut first = 0;
        let mut i = 0;
        let n = cs.clone().len();
        loop {
            if i == n {
                break;
            }
            let c = cs.get(i).unwrap();
            match c {
                _ if c.is_digit(10) => {
                    first += c.to_digit(10).unwrap();
                    break;
                }
                _ if *c == 'o' && n > i + 2 => {
                    if cs.get(i + 1).unwrap() == &'n' && cs.get(i + 2).unwrap() == &'e' {
                        first += 1;
                        break;
                    }
                    i += 1;
                }
                _ if c == &'t' => {
                    if n > i + 2 {
                        if cs.get(i + 1).unwrap() == &'w' && cs.get(i + 2).unwrap() == &'o' {
                            first += 2;
                            break;
                        }
                    }
                    if n > i + 4 {
                        if cs.get(i + 1).unwrap() == &'h'
                            && cs.get(i + 2).unwrap() == &'r'
                            && cs.get(i + 3).unwrap() == &'e'
                            && cs.get(i + 4).unwrap() == &'e'
                        {
                            first += 3;
                            break;
                        }
                    }
                    i += 1;
                }
                _ if c == &'f' && n > i + 3 => {
                    if cs.get(i + 1).unwrap() == &'o'
                        && cs.get(i + 2).unwrap() == &'u'
                        && cs.get(i + 3).unwrap() == &'r'
                    {
                        first += 4;
                        break;
                    } else if cs.get(i + 1).unwrap() == &'i'
                        && cs.get(i + 2).unwrap() == &'v'
                        && cs.get(i + 3).unwrap() == &'e'
                    {
                        first += 5;
                        break;
                    }
                    i += 1;
                }
                _ if c == &'s' => {
                    if n > i + 2 {
                        if cs.get(i + 1).unwrap() == &'i' && cs.get(i + 2).unwrap() == &'x' {
                            first += 6;
                            break;
                        }
                    }
                    if n > i + 4 {
                        if cs.get(i + 1).unwrap() == &'e'
                            && cs.get(i + 2).unwrap() == &'v'
                            && cs.get(i + 3).unwrap() == &'e'
                            && cs.get(i + 4).unwrap() == &'n'
                        {
                            first += 7;
                            break;
                        }
                    }
                    i += 1;
                }
                _ if c == &'e' && n > i + 4 => {
                    if cs.get(i + 1).unwrap() == &'i'
                        && cs.get(i + 2).unwrap() == &'g'
                        && cs.get(i + 3).unwrap() == &'h'
                        && cs.get(i + 4).unwrap() == &'t'
                    {
                        first += 8;
                        break;
                    }
                    i += 1;
                }
                _ if c == &'n' && n > i + 3 => {
                    if cs.get(i + 1).unwrap() == &'i'
                        && cs.get(i + 2).unwrap() == &'n'
                        && cs.get(i + 3).unwrap() == &'e'
                    {
                        first += 9;
                        break;
                    }
                    i += 1;
                }

                _ => i += 1,
            }
        }
        first *= 10;
        i = 0;
        cs.reverse();
        loop {
            if i == n {
                break;
            }
            let c = cs.get(i).unwrap();
            match c {
                _ if c.is_digit(10) => {
                    first += c.to_digit(10).unwrap();
                    break;
                }
                _ if c == &'e' => {
                    if n > i + 2 {
                        if cs.get(i + 1).unwrap() == &'n' && cs.get(i + 2).unwrap() == &'o' {
                            first += 1;
                            break;
                        }
                    }
                    if n > i + 4 {
                        if cs.get(i + 1).unwrap() == &'e'
                            && cs.get(i + 2).unwrap() == &'r'
                            && cs.get(i + 3).unwrap() == &'h'
                            && cs.get(i + 4).unwrap() == &'t'
                        {
                            first += 3;
                            break;
                        }
                    }
                    if n > i + 3 {
                        if cs.get(i + 1).unwrap() == &'v'
                            && cs.get(i + 2).unwrap() == &'i'
                            && cs.get(i + 3).unwrap() == &'f'
                        {
                            first += 5;
                            break;
                        }
                        if cs.get(i + 1).unwrap() == &'n'
                            && cs.get(i + 2).unwrap() == &'i'
                            && cs.get(i + 3).unwrap() == &'n'
                        {
                            first += 9;
                            break;
                        }
                    }
                    i += 1;
                }
                _ if c == &'o' => {
                    if n > i + 2 {
                        if cs.get(i + 1).unwrap() == &'w' && cs.get(i + 2).unwrap() == &'t' {
                            first += 2;
                            break;
                        }
                    }
                    i += 1;
                }
                _ if c == &'r' && n > i + 3 => {
                    if cs.get(i + 1).unwrap() == &'u'
                        && cs.get(i + 2).unwrap() == &'o'
                        && cs.get(i + 3).unwrap() == &'f'
                    {
                        first += 4;
                        break;
                    }
                    i += 1;
                }
                _ if c == &'x' => {
                    if n > i + 2 {
                        if cs.get(i + 1).unwrap() == &'i' && cs.get(i + 2).unwrap() == &'s' {
                            first += 6;
                            break;
                        }
                    }
                    i += 1;
                }
                _ if c == &'n' && n > i + 4 => {
                    if cs.get(i + 1).unwrap() == &'e'
                        && cs.get(i + 2).unwrap() == &'v'
                        && cs.get(i + 3).unwrap() == &'e'
                        && cs.get(i + 4).unwrap() == &'s'
                    {
                        first += 7;
                        break;
                    }
                    i += 1;
                }
                _ if c == &'t' && n > i + 4 => {
                    if cs.get(i + 1).unwrap() == &'h'
                        && cs.get(i + 2).unwrap() == &'g'
                        && cs.get(i + 3).unwrap() == &'i'
                        && cs.get(i + 4).unwrap() == &'e'
                    {
                        first += 8;
                        break;
                    }
                    i += 1;
                }

                _ => i += 1,
            }
        }
        sum += first as u64;
    };
    lines.for_each(move |l| {
        closure(String::from(l));
    });
    println!("{}", sum);
}
