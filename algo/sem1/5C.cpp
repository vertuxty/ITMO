#include "iostream"
#include "vector"
int main() {
    std::cin.tie(NULL);
    std::ios_base::sync_with_stdio(false);
    int n;
    std::cin >> n;
    std::vector<int> v(n);
    std::vector<int> res;
    for (int i = 0; i < n; i++) {
        std::cin >> v[i];
    }
    std::vector<int> dp(n + 1);
    std::vector<int> pos(n + 1);
    std::vector<int> prev(n);
    int l = 0;
    pos[0] = -1;
    dp[0] = INT_MIN;
    for (int i = 1; i < n + 1; i++) {
        dp[i] = INT_MAX;
    }
    for (int i = 0; i < n; i++) {
        auto k = std::upper_bound(dp.begin(), dp.end(), v[i]);
        int j = k - dp.begin();
//        std::cout << j << " j" << "\n";
        if (dp[j - 1] < v[i] && v[i] < dp[j]) {
            dp[j] = v[i];
            pos[j] = i;
            prev[i] = pos[j - 1];
            l = std::max(l, j);
        }
    }
//    for (int i = 0; i < n; i++) {
//        std::cout << prev[i];
//    }
//    std::cout << pos.size() << " " << l << " " << pos[l];
    int ind = pos[l];
    while (ind >= 0) {
//        std::cout << ind << " " << v[ind] << "\n";
        res.push_back(v[ind]);
//        std::cout << prev[ind] << " prev" << "\n";
        ind = prev[ind];
    }
    std::cout << res.size() << "\n";
    int i;
    for (i = res.size() - 1; i >= 0; i--) { std::cout << res[i] << " "; }
    return 0;
};