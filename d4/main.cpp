#include <cstdio>
#include <map>
#include <string>
#include <vector>
#include <iostream>
#include <fstream>
#include <set>
#include <sstream>

using namespace std;

void part_one(stringstream &input_buffer) {
  string s;
  int card_num;
  char c;

  int accum = 0;

  while (input_buffer >> s >> card_num >> c) {
    set<int> winners;
    vector<int> numbers;
    string line;

    string str;
    getline(input_buffer, str);

    stringstream stream;
    stream << str;

    while (stream >> str && str != "|") {
      winners.insert(stoi(str));
    }
    while (stream >> str) {
      numbers.push_back(stoi(str));
    }

    int score = 0;

    for (auto n : numbers) {
      if (winners.contains(n)) {
        if (score == 0) {
          score = 1;
        } else {
          score *= 2;
        }
      }
    }

    accum += score;
  }

  printf("%d\n", accum);
}

void part_two(stringstream &input_buffer) {
  string s;
  int card_num;
  char c;

  unsigned long long accum = 0;
  vector<unsigned long long> done(256, 0);

  for (int i = 1; i <= 211; i++) {
    done[i] = 1;
  }

  while (input_buffer >> s >> card_num >> c) {
    set<int> winners;
    vector<int> numbers;
    string line;

    string str;
    getline(input_buffer, str);

    stringstream stream;
    stream << str;

    while (stream >> str && str != "|") {
      winners.insert(stoi(str));
    }
    while (stream >> str) {
      numbers.push_back(stoi(str));
    }

    unsigned long long matches = 0;

    for (auto n : numbers) {
      if (winners.contains(n)) {
        matches++;
      }
    }

    for (int i = card_num + 1; i <= card_num + matches; i++) {
      // printf("%d: %d: %llu, ", card_num, i, done[card_num]);
      done[i] += done[card_num];
    }
  }

  for (auto count : done) {
    accum += count;
  }
  
  printf("%llu\n", accum);
}

int main() {
  ifstream input_file("input");
  stringstream input_buffer;
  input_buffer << input_file.rdbuf();

  // part_one(input_buffer);
  part_two(input_buffer);
    
  return 0;
}
