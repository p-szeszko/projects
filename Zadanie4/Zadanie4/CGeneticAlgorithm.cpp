#include "CGeneticAlgorithm.h"





CGeneticAlgorithm::CGeneticAlgorithm()
{
	
}

bool CGeneticAlgorithm::initialize(int pop_size, double cross_prob, double mut_prob, int iteration, CKnapsackProblem &sack)
{
	
	created = true;
	if (pop_size <= 0)
	{
		cout << BAD_POP << endl;
		created = false;
	}
	if (cross_prob <= 0 || cross_prob > 1)
	{
		cout << BAD_CROSS << endl;
		created = false;
	}
	if (mut_prob <= 0 || mut_prob > 1)
	{
		cout << BAD_MUTATE << endl;
		created = false;
	}
	if (iteration <= 0)
	{
		cout << BAD_ITERATION << endl;
		created = false;
	}
	if (created) {
		this->pop_size = pop_size;
		this->cross_prob = cross_prob;
		this->mut_prob = mut_prob;
		this->iteration = iteration;
	}
	else
	{
		cout << NOT_CREATED << endl;
			delete this;
	}
	this->sack = &sack;
}


CGeneticAlgorithm::~CGeneticAlgorithm()
{
	for (int i = 0; i < population.size(); i++)
	{
		delete population[i];
	}
	delete best_fitted;
}

void CGeneticAlgorithm::generate_first_pop()
{
	mt19937 gen(rd());
	uniform_int_distribution<> dis(0, 1);
	for (int i = 0; i < pop_size; i++)
	{
		vector<int> genotype;
		for (int j = 0; j < sack->get_items(); i++)
		{
			int gen = dis(gen);
			genotype.push_back(gen);
		}
		CIndividual *member = new CIndividual(genotype,sack);
		member->eval_fitness();
		population.push_back(member);
	}
}

void CGeneticAlgorithm::set_best()
{
	for (int i = 0; i < population.size(); i++)
	{
		if (population[i]->get_fitness() > best_fitted->get_fitness())
		{
			
			best_fitted = population[i];
		}
	}
}

void CGeneticAlgorithm::cross_all()
{
	
	vector<CIndividual*> new_generation;
	mt19937 gen(rd());
	uniform_int_distribution<> dis(0, population.size());
	uniform_int_distribution<> dis2(0, sack->get_items()-1);
	uniform_real_distribution<> dis3(0.0, 1.0);
	int par1 = dis(gen);
	int par2 = dis(gen);
	double cross = dis3(gen);
	while (new_generation.size() != population.size()) {
		if (cross <= cross_prob) {
			CIndividual *child1;
			CIndividual *child2;
			population[par1]->cross(*(population[par2]), dis2(gen), *(child1), *(child2));
			new_generation.push_back(child1);
			new_generation.push_back(child2);
		}
		else
		{
			new_generation.push_back(population[par1]);
			new_generation.push_back(population[par2]);
		}

	}
	population.clear();
	this->population = new_generation;
	
}

void CGeneticAlgorithm::mutate_all()
{
	mt19937 gen(rd());
	uniform_real_distribution<> dis(0.0, 1.0);
	for (int i = 0; i < population.size(); i++)
	{
		for (int j = 0; j < population[i]->get_genotype().size(); j++)
		{
			double mutate = dis(gen);
			if (mutate <= mut_prob)
			{
				population[i]->mutate(j);
			}
		}

	}

}

void CGeneticAlgorithm::iterate()
{
	generate_first_pop();
	set_best();

	for (int i = 0; i < pop_size; i++)
	{
		cross_all();
		mutate_all();
		set_best();



	}


}



void CGeneticAlgorithm::eval_all()
{
	for (int i = 0; i < population.size(); i++)
	{
		population[i]->eval_fitness();
	}
	

}
