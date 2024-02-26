#include "vector"
#include "iostream"
#include "stack"
int main() {
    int n;
    int h;
    std::cin >> n;
    std::stack<std::pair<int, int>> st;
    st.push({0, -1});
    long long ans = 0;
    for (int i = 1; i <= n+1; i++) {
        if (i <= n) {
            std::cin >> h;
        } else {
            h = 0;
        }
        int z = i;
        while (h <= st.top().second) {
            z = st.top().first;
            int h1 = st.top().second;
            st.pop();
            long long area = (long long) h1*(i - z);
            if (area > ans) {
                ans = area;
            }
        }
        st.push({z, h});
    }
    std::cout << ans;
    return 0;
};