#include "iostream"
#include "queue"
int main() {
    std::cin.tie(NULL);
    std::ios_base::sync_with_stdio(false);
    int n;
    int k;
    std::cin >> n >> k;
    std::deque<int> q1, q2;
    int tmp;
    for (int i = 0; i < k; i++) {
        std::cin >> tmp;
        q1.push_back(tmp);
        while (!q2.empty() && q2.back() > tmp)
            q2.pop_back();
        q2.push_back(tmp);
    }
    for (int i = k; i < n; i++) {
        std::cin >> tmp;
        std::cout << q2.front() << "\n";
        if (q1.front() == q2.front()) {
            q1.pop_front();
            q2.pop_front();
            q1.push_back(tmp);
        } else {
            q1.pop_front();
            q1.push_back(tmp);
        }
        while (!q2.empty() && q2.back() > tmp)
            q2.pop_back();
        q2.push_back(tmp);
    }
    std::cout << q2.front() << "\n";
    return 0;
};