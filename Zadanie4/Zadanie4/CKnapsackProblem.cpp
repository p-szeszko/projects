#include "CKnapsackProblem.h"



CKnapsackProblem::CKnapsackProblem()
{
}


CKnapsackProblem::~CKnapsackProblem()
{
}

CKnapsackProblem::CKnapsackProblem(int cap, int items)
{

	created = true;
	if (cap <= 0)
	{
		cout << BAD_SACK << endl;
		created = false;
	}
	if (items <= 0)
	{
		cout << BAD_ITEM << endl;
		created = false;
	}
}

CKnapsackProblem::CKnapsackProblem(int cap, int items, vector<int> values, vector<int> weights)
{
	created = true;
	if (cap <= 0)
	{
		cout << BAD_SACK << endl;
		created = false;
	}
	if (items <= 0)
	{
		cout << BAD_ITEM << endl;
		created = false;
	}
	if (items != weights.size())
	{
		cout << BAD_SIZE << endl;
		created = false;
	}
	if (values.size() != weights.size())
	{
		cout << DIF_SIZES << endl;
		created = false;
	}

	if (created) {
		this->capacity = cap;
		this->items = items;
		this->values = values;
		this->weights = weights;
	}
	else {
		cout << DELETE_THIS << endl;
		delete this;
	}
}

int CKnapsackProblem::eval_fittnes(CIndividual &to_eval)
{
	int fittnes = 0;
	int size = 0;
	for (int i = 0; i < to_eval.get_genotype.size(); i++)
	{
		if (to_eval.get_genotype()[i] == 1)
		{
			fittnes = fittnes + values[i];
			size = size + weights[i];
		}
	}
	if (size > this->capacity)
	{
		return 0;
	}
	else
		return fittnes;
}

int CKnapsackProblem::get_items()
{
	return this->items;
}

