#include "iostream"
#include "vector"
#include "string"
#include <cmath>
#include "deque"
//#pragma gcc optimize ('O3,unroll_loops')
//#pragma GCC target("avx2,bmi,bmi2,lzcnt,popcnt")
int main() {
    int n;
    std::ios::sync_with_stdio(false);
    std::cin.tie(nullptr);
    std::cin >> n;
    char ch;
    std::vector<int> ways(n);
    std::vector<char> charse(n);
    for (int i = 0; i < n; i++) { //инициализация
        std::cin >> ch;
        charse[i] = ch;
        if (ch == '.') {
            ways[i] = 0;
        } else if (ch == 'w') {
            ways[i] = -1002;
        } else {
            ways[i] = 1;
        }
    }
//    ways[3] += std::max(ways[2], ways[0]);
    ways[2] += ways[1];
    for (int i = 3; i < n; i++) {
//        std::cin >> ch;
        if (i > 4) {
            ways[i] += std::max(std::max(ways[i - 5], ways[i - 3]), ways[i - 1]);
        } else if (i > 2) {
            ways[i] += std::max(ways[i - 1], ways[i - 3]);
        }
    }
    if (ways[n - 1] < 0) {
        std::cout << "-1";
    } else if (ways[n - 1] > 0){
        std::cout << ways[n - 1];
    } else {
        std::cout << "0";
    }
    return 0;
};