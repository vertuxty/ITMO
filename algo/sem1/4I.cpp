#include "iostream"
#include "queue"

int main() {
    std::cin.tie(NULL);
    std::ios_base::sync_with_stdio(false);
    int n, d;
    std::cin >> n;
    std::deque<long long> q;
    long long diff_y = 0;
    long long diff_x = 0;
    int prevD = 0;
    while (n > 0) {
        std::cin >> d;
        if (d == 1) {
            long long tmp;
            std::cin >> tmp;
            if (prevD == 3) {
                if (!q.empty() && diff_y > 0) {
                    for (int i = 0; i < q.size(); i++) {
                        q[i] = q[i] + diff_y;
//                        std::cout << q[i] << " q[i]" << "\n";
                    }
                    diff_y = 0;
                }
            } else if (prevD == 2) {
                if (q.size() > 1 && diff_y > 0) {
                    for (int i = 1; i < q.size(); i++) {
                        q[i] = q[i] + diff_y;
//                        std::cout << q[i] << " q[i]" << "\n";
                    }
                    diff_y = 0;
                }
                if (!q.empty()) {
                    q[0] += diff_x;
                    diff_x = 0;
                }
            }
            diff_y = 0;
            q.push_back(tmp);
            prevD = 1;
        } else if (d == 2) {
            if (prevD == 3) {
                diff_x += diff_y;
            }
            long long x;
            long long y;
            std::cin >> x >> y;
            diff_y += y;
//            std::cout << diff_y << " diff_y" << "\n";
            diff_x += x;
//            std::cout << diff_x << " diff_x" << "\n";
            prevD = 2;
        } else if (d == 3) {
            if (prevD == 1) {
                std::cout << q.front() << "\n";
            } else if (prevD == 2) {
                std::cout << q.front() + diff_x << "\n";
                diff_x = 0;
            } else if (prevD == 3) {
                std::cout << q.front() + diff_y << "\n";
            }
            q.pop_front();
            prevD = 3;
        }
        n--;
    }
    return 0;
};