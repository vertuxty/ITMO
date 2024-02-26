#include "iostream"
#include "stack"
#include "vector"
int main() {
    std::cin.tie(NULL);
    std::ios_base::sync_with_stdio(false);
    int n;
    std::cin >> n;
    std::stack<std::pair<std::pair<long long, long long>, int>> s;
    std::vector<std::pair<long long, int>> v;
    std::vector<std::pair<long long, int>> v2;
    long long tmp;
    long long diff = 0;
    for (int i = 0; i < n; i++) {
        std::cin >> tmp;
        if (!s.empty()) {
            while (!s.empty() && s.top().first.first == 0) {
                v.emplace_back(s.top().first.first + s.top().first.second, s.top().second);
                s.pop();
            }
            while (!s.empty() && s.top().first.first < tmp) {
                v.emplace_back(s.top().first.first - 1 + s.top().first.second, s.top().second);
                s.pop();
                diff++;
            }
        }
        s.push({{tmp, diff}, i});
        diff = 0;
    }

    while (!s.empty()) {
        v.emplace_back(s.top().first.first + s.top().first.second, s.top().second);
        s.pop();
    }

    std::sort(v.begin(), v.end(), [] (const auto &x, const auto &y) { return x.second < y.second; });
    std::reverse(v.begin(), v.end());
//    for (int i = 0; i < n; i++) {
//        std::cout << v[i].first << " ";
//    }
//    std::cout << "\n";

    for (int i = 0; i < n; i++) {
        tmp = v[i].first;
        if (!s.empty()) {
            while (!s.empty() && s.top().first.first == 0) {
                v2.emplace_back(s.top().first.first + s.top().first.second, s.top().second);
                s.pop();
            }
            while (!s.empty() && s.top().first.first < tmp) {
                v2.emplace_back(s.top().first.first - 1 + s.top().first.second, s.top().second);
                s.pop();
                diff++;
            }
        }
        s.push({{tmp, diff}, i});
        diff = 0;
    }

    while (!s.empty()) {
        v2.emplace_back(s.top().first.first + s.top().first.second, s.top().second);
        s.pop();
    }
    long long maxim = 0;
    for (int i = 0; i < n; i++) {
        maxim = std::max(maxim, v2[i].first);
//        std::cout << v2[i].first << " ";
    }
//    std::cout << "\n";
    std::cout << maxim;
    return 0;
};