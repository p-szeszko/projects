#include "CIndividual.h"



CIndividual::CIndividual()
{
	this->fitness = 0;
}


CIndividual::~CIndividual()
{
}

CIndividual::CIndividual(vector<int> genotype, CKnapsackProblem* problem)
{
	this->genotype = genotype;
	this->problem = problem;
}

CIndividual::CIndividual(CIndividual & other)
{
	this->genotype = other.get_genotype();
	this->fitness = fitness;
	this->problem = problem;
}

void CIndividual::eval_fitness()
{
	this->fitness=this->problem->eval_fittnes(*this);

}

int CIndividual::get_fitness()
{
	return this->fitness;
}

vector<int>& CIndividual::get_genotype()
{
	return this->genotype;
}

void CIndividual::mutate(int gen_nr)
{
	if (gen_nr < 0||gen_nr>=genotype.size())
	{

	}
	else
	genotype[gen_nr] = (genotype[gen_nr] + 1) % 2;



}

void CIndividual::cross(CIndividual & parent, int division, CIndividual & child1, CIndividual & child2)
{
	vector<int> heritance1;
	vector<int> heritance2;

	for (int i = 0; i < division; i++)
	{
		heritance1[i] = this->genotype[i];
		heritance2[i] = parent.get_genotype()[i];
	}
	for (int i = division; i < this->genotype.size(); i++)
	{
		heritance1[i] = parent.get_genotype()[i];
		heritance2[i] = this->genotype[i];
	}

	child1 = *(new CIndividual(heritance1, problem));
	child2 = *(new CIndividual(heritance2, problem));



}

