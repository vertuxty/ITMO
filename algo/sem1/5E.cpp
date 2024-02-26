#include "iostream"
#include "vector"
int main() {
    std::cin.tie(NULL);
    std::ios_base::sync_with_stdio(false);
    std::string s1, s2;
    std::cin >> s1 >> s2;
    int n = s1.size();
    int m = s2.size();
    std::vector<std::vector<int>> dp;
    dp.assign(std::max(n, m) + 1, std::vector<int>(std::max(n, m) + 1));
    dp[0][0] = 0;
    for (int i = 1; i < n + 1; i++) {
        dp[i][0] = i;
    }
    for (int j = 1; j < m + 1; j++) {
        dp[0][j] = j;
    }
    for (int i = 1; i < n + 1; i++) {
        for (int j = 1; j < m + 1; j++) {
            if (s1[i - 1] == s2[j - 1]) {
                dp[i][j] = std::min(std::min(dp[i-1][j] + 1, dp[i][j - 1] + 1), dp[i-1][j-1]);
            } else {
                dp[i][j] = std::min(std::min(dp[i-1][j] + 1, dp[i][j - 1] + 1), dp[i-1][j-1] + 1);
            }
        }
    }
    std::cout << dp[n][m];


    return 0;
};