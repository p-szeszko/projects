#pragma once
#include <vector>
#include <iostream>
#include "CKnapsackProblem.h"
#include "CGeneticAlgorithm.h"

using namespace std;
class CIndividual
{
public:
	CIndividual();
	~CIndividual();
	CIndividual(vector <int> genotype, CKnapsackProblem* problem);
	CIndividual(CIndividual &other);
	void eval_fitness();
	int get_fitness();
	vector<int>& get_genotype();
	void mutate(int gen_nr);
	void cross(CIndividual &parent, int division, CIndividual &child1, CIndividual &child2);


private:
	vector<int> genotype;
	int fitness;
	CKnapsackProblem* problem;


};