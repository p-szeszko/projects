#include "CGeneticAlgorithm.h"
#include "CIndividual.h"
#include "CKnapsackProblem.h"
#include <vector>
#include <iostream>

using namespace std;

int main()
{
	vector <int> values = { 1,2,3,5 };
	vector <int> weights = { 4,3,3,8 };
	CKnapsackProblem *sack = new CKnapsackProblem(10, 4, values, weights);
	CGeneticAlgorithm *ag = new CGeneticAlgorithm();
	ag->initialize(4, 0.6, 0.1, 4, *sack);
	ag->iterate();




}