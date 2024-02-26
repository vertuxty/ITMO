#include "iostream"
#include "vector"
int main() {
    std::cin.tie(NULL);
    std::ios_base::sync_with_stdio(false);
    int n, m;
    std::cin >> n >> m;
    int a[n][m];
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            std::cin >> a[i][j];
        }
    }
    std::vector<std::vector<int>> dp;
    dp.assign(n, std::vector<int>(m));
    dp[0][0] = a[0][0];
    dp[1][0] = dp[0][0] + a[1][0];
    dp[0][1] = dp[0][0] + a[0][1];
    for (int i = 1; i < m; i++) {
        dp[0][i] = dp[0][i-1] + a[0][i];
    }
    for (int i = 1; i < n; i++) {
        dp[i][0] = dp[i-1][0] + a[i][0];
    }
    for (int i = 1; i < n; i++) {
        for (int j = 1; j < m; j++) {
            dp[i][j] = std::max(dp[i - 1][j], dp[i][j - 1]) + a[i][j];
        }
    }
    std::cout << dp[n - 1][m - 1] << "\n";
    std::vector<std::string> v;
    int m1 = dp[n-1][m-1] - a[n-1][m-1];
    int i = n - 1;
    int j = m - 1;
    while (i > 0 && j > 0) {
        if (m1 == dp[i][j - 1]) {
            m1 = dp[i][j - 1] - a[i][j - 1];
            v.emplace_back("R");
            j--;
        } else {
            m1 = dp[i - 1][j] - a[i - 1][j];
            v.emplace_back("D");
            i--;
        }
    }
    if (i == 0) {
        while (j > 0) {
            v.emplace_back("R");
            j--;
        }
    } else if (j == 0) {
        while (i > 0) {
            v.emplace_back("D");
            i--;
        }
    }

    for (int i = v.size() - 1; i >= 0; i--) {
        std::cout << v[i];
    }
    return 0;
};