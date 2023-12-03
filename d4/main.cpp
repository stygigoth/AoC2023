#include <iostream>
#include <fstream>
#include <sstream>

int main() {
  std::ifstream input_file("input");
  std::stringstream input_buffer;
  input_buffer << input_file.rdbuf();
  std::cout << input_buffer.str();
  return 0;
}
