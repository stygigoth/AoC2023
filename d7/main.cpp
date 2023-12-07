#include <algorithm>
#include <cstddef>
#include <fstream>
#include <iostream>
#include <limits>
#include <map>
#include <ostream>
#include <string>
#include <utility>
#include <vector>

using namespace std;

vector<string> lines;
vector<pair<string, int>> hands_one;
vector<pair<string, int>> hands_two;
map<char, int> value_one = {{'A', 14}, {'K', 13}, {'Q', 12}, {'J', 11}, {'T', 10}, {'9', 9},  {'8', 8},  {'7', 7},  {'6', 6},  {'5', 5}, {'4', 4},  {'3', 3},  {'2', 2}};
map<char, int> value_two= {{'A', 14}, {'K', 13}, {'Q', 12}, {'T', 10}, {'9', 9},  {'8', 8},  {'7', 7},  {'6', 6},  {'5', 5}, {'4', 4},  {'3', 3},  {'2', 2}, {'J', 1}};

void parse_lines() {
  for (const auto& currline : lines) {
    size_t space_pos = currline.find(" ");
    string hand = currline.substr(0, space_pos);
    int bid = stoi(currline.substr(space_pos + 1));
    hands_one.push_back(make_pair(hand, bid));
    hands_two.push_back(make_pair(hand, bid));
  }
}

pair<int, int> repeated_chars(string input, bool jokers) {
  map<char, int> char_count;
  if (!jokers) {
    for (int i = 0; i < input.size(); i++) {
      char_count[input[i]]++;
    }
  } else {
    for (int i = 0; i < input.size(); i++) {
      if (input[i] != 'J') {  
        char_count[input[i]]++;
      }
    }
  }
  
  int max = 0;
  int second_max = 0;
  for (auto i = char_count.begin(); i != char_count.end(); i++) {
    if (i->second > max) {
      second_max = max;
      max = i->second;
    } else if (i->second > second_max) {
      second_max = i->second;
    }
  }

  return {max, second_max};
}

bool cmp_one(const pair<string, int>& a, const pair<string, int>& b) {
  auto a_chars = repeated_chars(a.first, false);
  auto b_chars = repeated_chars(b.first, false);

  if (a_chars.first != b_chars.first) {
    return a_chars.first < b_chars.first;
  }

  if (a_chars.first == 3 && b_chars.first == 3) {
    if (a_chars.second != b_chars.second) {
      return a_chars.second < b_chars.second;
    }
  }

  if (a_chars.first == 2 && b_chars.first == 2) {
    if (a_chars.second != b_chars.second) {
      return a_chars.second < b_chars.second;
    }
  }

  for (int i = 0; i < a.first.size(); i++) {
    if (value_one.at(a.first[i]) != value_one.at(b.first[i])) {
      return value_one.at(a.first[i]) < value_one.at(b.first[i]);
    }
  }

  return false;
}

void part_one() {
  int sum = 0;
  
  sort(hands_one.begin(), hands_one.end(), cmp_one);

  for (int i = 0; i < hands_one.size(); i++) {
    sum += hands_one[i].second * (i + 1);
  }

  cout << "Part one: " << sum << endl;
}

int num_jokers(string input) {
  int count = 0;
  for (int i = 0; i < input.size(); i++) {
    if (input[i] == 'J') {
      count++;
    }
  }
  return count;
}

bool cmp_two(const pair<string, int> &a, const pair<string, int> &b) {
        auto a_chars = repeated_chars(a.first, true);
        auto b_chars = repeated_chars(b.first, true);
        int a_jokers = num_jokers(a.first);
        int b_jokers = num_jokers(b.first);

        int new_max_a = a_chars.first + a_jokers;
        int new_max_b = b_chars.first + b_jokers;

        if (new_max_a != new_max_b) {
            return new_max_a < new_max_b;
        }

        if (new_max_b == new_max_a && new_max_b == 3) {
            if (a_chars.second != b_chars.second) {
                return a_chars.second < b_chars.second;
            }
        }
        if (new_max_b == new_max_a && new_max_b == 2) {
            if (a_chars.second != b_chars.second) {
                return a_chars.second < b_chars.second;
            }
        }

        for (int i = 0; i < a.first.size(); i++) {
            if (value_two.at(a.first[i]) != value_two.at(b.first[i])) {
                return value_two.at(a.first[i]) < value_two.at(b.first[i]);
            }
        }
        return false;
    }

void part_two() {
  int sum = 0;

  sort(hands_two.begin(), hands_two.end(), cmp_two);

  for (int i = 0; i < hands_two.size(); i++) {
    sum += hands_two[i].second * (i + 1);
  }

  cout << "Part two: " << sum << endl;
}

int main() {
  string currline;
  ifstream file("input");
  
  while (getline(file, currline)) {
    lines.push_back(currline);
  }

  parse_lines();
  part_one();
  part_two();
  
  file.close();
  return 0;
}
