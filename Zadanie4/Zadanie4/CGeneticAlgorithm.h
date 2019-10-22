#pragma once
#include <vector>
#include <iostream>
#include "CIndividual.h"
#include "CKnapsackProblem.h"
#include <random>
using namespace std;
static const string BAD_POP = "Zly rozmiar populacji";
static const string BAD_CROSS = "Zle prawd. krzyzowania";
static const string BAD_MUTATE = "Zle prawd. mutacji";

static const string BAD_ITERATION = "Potrzebna dodatnia ilosc iteracji";
static const string NOT_CREATED = "Nie udalo sie uruchomic AG, usuwam obiekt";

class CGeneticAlgorithm
{
public:
	CGeneticAlgorithm();
	bool initialize(int pop_size, double cross_prob, double mut_prob, int iteration, CKnapsackProblem & sack);
	~CGeneticAlgorithm();
	void generate_first_pop();  // generuje wstepna populacje
	void set_best();
	void cross_all();  // krzyzuje dwa osobniki( przy uzyciu funkcji z CIndividual
	void mutate_all(); // wywoluje mutate dla kazdego osobnika populacji
	void iterate();
	void eval_all();



private:
	CKnapsackProblem *sack;
	CIndividual *best_fitted;
	int pop_size;
	double cross_prob;
	double mut_prob;
	int iteration;
	bool created;
	random_device rd;
	vector <CIndividual*> population;
};

