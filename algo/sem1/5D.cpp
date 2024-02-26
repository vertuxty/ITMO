#include "iostream"
#include "vector"
#include "string"
#include <cmath>

int main() {
    int n, m;
    std::cin.tie(NULL);
    std::ios_base::sync_with_stdio(false);
    std::cin >> n;
    std::vector<int> first(n + 1);
    for (int i = 0; i < n; i++) {
        std::cin >> first[i + 1];
    }
    std::cin >> m;
    std::vector<int> second(m + 1);
    for (int i = 0; i < m; i++) {
        std::cin >> second[i + 1];
    }
    int dp[n + 1][m + 1]; //Динамика наибольшей длинны
    std::pair<int, int> prev[n + 1][m + 1];
    for (int i = 0 ; i < n + 1; i++) {
        dp[i][0] = 0;
    }

    for (int j = 0; j < m + 1; j++) {
        dp[0][j] = 0;
    }
    for (int i = 1; i < n + 1; i++) {
        for (int j = 1; j < m + 1; j++) {
            if (first[i] == second[j]) {
                dp[i][j] = dp[i-1][j-1] + 1;
                prev[i][j] = std::make_pair(i - 1, j - 1);
            } else {
                dp[i][j] = std::max(dp[i][j-1], dp[i - 1][j]);
                if (dp[i][j - 1] >= dp[i - 1][j]) {
                    prev[i][j] = std::make_pair(i, j - 1);
                } else {
                    prev[i][j] = std::make_pair(i - 1, j);
                }
            }
        }
    }
    std::cout << dp[n][m] << "\n"; //длинна
    std::vector<int> lcs;
    int k1 = n;
    int k2 = m;
    while (k1 > 0 && k2 > 0) {
//        std::cout << prev[k1][k2].first + 1<< " " << prev[k1][k2].second + 1 << "\n";
        if (prev[k1][k2].first == k1 - 1 && prev[k1][k2].second == k2 - 1) {
            lcs.push_back(first[k1]);
            k1--;
            k2--;
        } else if (prev[k1][k2].first == k1 - 1 && prev[k1][k2].second == k2) {
            k1--;
        } else {
            k2--;
        }
    }
    for (int i = lcs.size() - 1; i >= 0; i--) {
        std::cout << lcs[i] << " ";
    }
//    std::cout<<lcs.size();
};