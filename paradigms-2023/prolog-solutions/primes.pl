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








%unique_prime_divisors(1, []) :- !.
%unique_prime_divisors(N, Divisors) :- prime(N), Divisors = [N], !.
%unique_prime_divisors(N, Divisors) :- find_unique_d(N, sqrt(N), 2, Divisors), !.
%
%divide_while_true(N, DIV, RES) :- N < DIV, RES is N, !.
%divide_while_true(N, DIV, RES) :- R is mod(N, DIV), R\=0, RES is N, !.
%divide_while_true(N, DIV, RES) :-
%	R is mod(N, DIV),
%	R == 0,
%	N1 is div(N, DIV),
%	divide_while_true(N1, DIV, RES).
%
%find_unique_d(N, S, R, []):- R > N, !.
%find_unique_d(N, S, H, [H | T]) :-
%	N >= H,
%	prime(H),
%	R is mod(N, H),
%	R == 0,
%	HEAD2 is H,
%	divide_while_true(N, H, RES),
%	find_unique_d(RES, S, HEAD2,T).
%
%find_unique_d(N, S, H, T) :-
%	HEAD2 is H + 1,
%	find_unique_d(N, S, HEAD2, T).
%
%nth_prime(N, P) :- count_nth(N, 1, 2, P).
%
%count_nth(N, CURRNTH, CURR_PRIME, P) :-
%	 N == CURRNTH,
%	 P is CURR_PRIME, !.
%	
%count_nth(N, CURRNTH, CURR_PRIME, P) :-
%	 N \= CURRNTH,
%	 while_not_prime(CURR_PRIME, NEW_PRIME, -1),
%	 NEW_CURRNTH is CURRNTH + 1,
%	 count_nth(N, NEW_CURRNTH, NEW_PRIME, P).
%
%
%while_not_prime(CURR, NEW_P, FLAG) :-
%	prime(CURR),
%	FLAG == -1,
%	N_CURR is CURR + 1,
%	while_not_prime(N_CURR, NEW_P, 0), !.
%while_not_prime(CURR, NEW_P, FLAG) :-
%	prime(CURR),
%	FLAG == 0,
%	NEW_P is CURR, !.
%while_not_prime(CURR, NEW_P, FLAG) :-
%	composite(CURR),
%	N_CURR is CURR + 1,
%	while_not_prime(N_CURR, NEW_P, FLAG), !.

	