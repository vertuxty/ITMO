composite(N) :- composite(N, 2, round(sqrt(N))).
composite(N, R, S) :- S > R, \+ (0 is mod(N, R)), R1 is R + 1, composite(N, R1, S), !.
composite(N, R, S) :- 0 is mod(N, R), \+ (2 is N), !.
prime(N) :- \+ composite(N).

is_list([]) :- true.
is_list([H | T]) :- number(H), prime(H), is_list(T). 

prime_divisors(N, Divisors) :- is_list(Divisors), prime_d(N, Divisors, N, -1), !.
prime_divisors(1, Divisors).
prime_divisors(N, Divisors) :- prime(N), Divisors = [N], !.
prime_divisors(N, Divisors) :- find_divisors(N, sqrt(N), 2, Divisors), !.


find_divisors(N, S, R,[]):- R > N, !.
find_divisors(N,S,HEAD1,[HEAD1|T]):-
		N >= HEAD1,
		prime(HEAD1),
    R is (N mod HEAD1),
    HEAD2 is HEAD1,
    NEW_N is div(N, HEAD1),
    R==0,
    find_divisors(NEW_N, S, HEAD2,T).      
find_divisors(N,S, HEAD1,T):-
		HEAD2 is HEAD1+1,
    find_divisors(N,S, HEAD2,T). 

prime_divisors(1, []) :- !. 
prime_divisors(N, []) :- \+ (N > 1), !.

prime_d(N, [], R, PREV):- (\+ R > 1).
prime_d(N, [HEAD | T], R, PREV) :- 
		R >= HEAD,
		HEAD >= PREV, 
		0 is mod(R, HEAD),
		R1 is div(R, HEAD), 
		PREV1 = HEAD,
		prime_d(N, T, R1, PREV1).


square_divisors(N, D) :- prime_divisors(N, S), make_bigger(S, D).
make_bigger([], D) :- D = []. 
make_bigger([H | T], D) :- make_bigger(T, D1), recure_add(D, D1, H, 2).

cube_divisors(N, D) :- prime_divisors(N, S), make_bigger_cube(S, D).
make_bigger_cube([], D) :- D = [].
make_bigger_cube([H | T], D) :- make_bigger_cube(T, D1), recure_add(D, D1, H, 3).
recure_add(D, D1, H, 0) :- D = D1, !.
recure_add(D, D1, H, CNT) :-
	CNT > 0,
	D2 = [H | D1],
	CNT2 is CNT - 1,
	recure_add(D, D2, H, CNT2).
