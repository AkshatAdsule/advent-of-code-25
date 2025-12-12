#include <climits>
#include <iostream>
#include <map>
#include <string>
#include <vector>

using namespace std;

using vector_iterator = vector<int>::const_iterator;
using memo_key = pair<ptrdiff_t, int>;

int solve_part1(const vector<int>& batteries) {
  int result = 0;
  for (size_t l = 0; l < batteries.size(); ++l) {
    for (size_t r = l + 1; r < batteries.size(); ++r) {
      result = max(result, (10 * batteries[l] + batteries[r]));
    }
  }

  return result;
}

long solve_part2(vector_iterator begin,
                 vector_iterator end,
                 int remaining,
                 map<memo_key, long>& memo) {
  // check if we have already cached this result:
  memo_key key = {distance(begin, end), remaining};
  if (memo.count(key))
    return memo[key];

  // base cases
  if (remaining == 0) {
    return 0;
  }
  if (begin == end) {
    return LLONG_MIN;
  }

  long value = (*begin) * pow(10, remaining - 1);

  long take_result = solve_part2(begin + 1, end, remaining - 1, memo);
  long skip_result = solve_part2(begin + 1, end, remaining, memo);

  long take = (take_result == LLONG_MIN) ? LLONG_MIN : value + take_result;
  long skip = skip_result;

  if (take == LLONG_MIN)
    return skip;
  if (skip == LLONG_MIN)
    return take;

  long result = max(take, skip);
  memo[key] = result;

  return result;
}

long long solve_part2(const vector<int>& batteries) {
  map<memo_key, long> memo;
  return solve_part2(batteries.begin(), batteries.end(), 12, memo);
}

int main() {
  string line;

  int result_part1 = 0;
  long long result_part2 = 0;
  while (getline(cin, line)) {
    vector<int> batteries;
    for (char c : line) {
      batteries.push_back(c - '0');
    }

    result_part1 += solve_part1(batteries);
    result_part2 += solve_part2(batteries);
  }

  cout << "Part 1 results " << result_part1 << endl;
  cout << "Part 2 results " << result_part2 << endl;
}
