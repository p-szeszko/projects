#pragma once
#include <vector>
#include <iostream>
#include "CGeneticAlgorithm.h"
#include "CIndividual.h"
#include <string>
using namespace std;
static const string BAD_SACK = "zla wielkosc plecaka";
static const string BAD_ITEM = "zla ilosc przedmiotow";
static const string BAD_SIZE = "tablica nie zgadza sie z iloscia przedmiotow";
static const string DIF_SIZES = "ilosc wag i ilosc wartosci sie nie zgadzaja, sprawdz tablice";
static const string DELETE_THIS = "popraw dane, usuwam sie";




class CKnapsackProblem
{
public:
	CKnapsackProblem();
	~CKnapsackProblem();
	CKnapsackProblem(int cap, int items);
	CKnapsackProblem(int cap, int items, vector<int> values, vector<int> weights);
	int eval_fittnes(CIndividual &to_eval);
	int get_items();
private:
	int capacity;
	int items;
	vector<int> values;
	vector<int> weights;
	bool created;

};

