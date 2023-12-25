use std::{
    collections::{HashMap, HashSet, VecDeque},
    fs,
};

use rand::prelude::SliceRandom;

struct Structure<'a> {
    connections: HashMap<&'a str, Vec<&'a str>>,
}

impl<'a> Structure<'a> {
    fn add_connection(&mut self, first: &'a str, second: &'a str) {
        if !self.connections.contains_key(first) {
            self.connections.insert(first, Vec::new());
        }
        if !self.connections.contains_key(second) {
            self.connections.insert(second, Vec::new());
        }
        let a = self.connections.get_mut(first).unwrap();
        a.push(second);
        let b = self.connections.get_mut(second).unwrap();
        b.push(first);
    }

    fn path_from(&self, a: &'a str, b: &'a str) -> Vec<&str> {
        let mut q = VecDeque::new();

        q.push_back((a, vec![a]));

        while let Some((curr, seen)) = q.pop_front() {
            if curr == b {
                return seen;
            }

            let next = self.connections.get(curr).unwrap();
            next.iter().filter(|x| !seen.contains(x)).for_each(|&n| {
                let mut nq = seen.clone();
                nq.push(n);
                q.push_back((n, nq));
            })
        }
        unreachable!()
    }

    fn remove_connection(&mut self, first: &str, second: &str) {
        let a = self.connections.get_mut(first).unwrap();
        let f_idx = a.iter().enumerate().find(|(_, &c)| c == second).unwrap().0;
        a.remove(f_idx);

        let b = self.connections.get_mut(second).unwrap();
        let g_idx = b.iter().enumerate().find(|(_, &c)| c == first).unwrap().0;
        b.remove(g_idx);
    }

    fn reachable(&self, from: &str) -> usize {
        let mut seen = HashSet::new();

        let mut q = VecDeque::new();

        q.push_back(from);

        while let Some(next) = q.pop_front() {
            if seen.contains(next) {
                continue;
            }

            seen.insert(next);

            self.connections
                .get(next)
                .unwrap()
                .iter()
                .for_each(|&c| q.push_back(c))
        }

        seen.len()
    }
}

fn main() {
    let raw_input: Vec<String> = fs::read_to_string("input")
        .unwrap()
        .trim()
        .split("\n")
        .map(|s| String::from(s))
        .collect();
    let input: Vec<_> = raw_input.iter().map(|l| l.as_str()).collect();

    let mut s = Structure {
        connections: HashMap::new(),
    };

    input.iter().for_each(|l| {
        let (name, conns) = l.split_once(": ").unwrap();
        conns.split(" ").for_each(|c| {
            s.add_connection(name, c);
        });
    });

    let node_names: Vec<&str> = s.connections.keys().map(|&x| x).collect();

    let mut path_counts: HashMap<String, i32> = HashMap::new();

    for _ in 0..300 {
        let mut chosen = node_names.choose_multiple(&mut rand::thread_rng(), 2);
        let a = *chosen.next().unwrap();
        let b = *chosen.next().unwrap();

        s.path_from(a, b).iter().for_each(|&n| {
            let count = path_counts.get(n).unwrap_or(&0);
            path_counts.insert(n.to_string(), count + 1);
        });
    }

    let mut count_vecs: Vec<_> = path_counts.iter().collect();
    count_vecs.sort_by(|a, b| b.1.cmp(a.1));

    let suspicious: Vec<_> = count_vecs.iter().take(6).map(|x| x.0.clone()).collect();

    let a = suspicious[0].as_str();
    let b = suspicious[1].as_str();
    let c = suspicious[2].as_str();
    let d = suspicious[3].as_str();
    let e = suspicious[4].as_str();
    let f = suspicious[5].as_str();

    println!("removing connection between {} and {}", a, b);
    s.remove_connection(a, b);

    println!("removing connection between {} and {}", c, d);
    s.remove_connection(c, d);

    println!("removing connection between {} and {}", e, f);
    s.remove_connection(e, f);

    let p1 = s.reachable(a);
    let p2 = s.reachable(b);
    println!("graph parts are {} and {}", p1, p2);
    println!("part one: {}", p1 * p2);
}
