# Labels
# Kids	Online_Activity	Education_Basic	Education_Graduation	Education_Master	Education_PhD	Marital_Status_Divorced
# Marital_Status_Married	Marital_Status_Single	Marital_Status_Together	Income_less_than_30k	Income_from_30k_to_50k
# Income_from_50k_to_75k	Income_above_75k	birth_older_than_1960	birth_from_1960_to_1975	birth_from_1975_to_1990	birth_younger_than_1990
from sklearn.neighbors import NearestNeighbors
import pandas as pd

class InputSampleParser:
  def __init__(self,sample):
    self.name = sample['name']
    self.number_of_samples = sample['no_of_samples']
    self.kids = sample['Kids']['kids'] / self.number_of_samples
    self.Online_Activity = sample['purchase_method']['Online'] / self.number_of_samples
    self.Education_Basic = sample['Education']['Basic'] / self.number_of_samples
    self.Education_Graduation = sample['Education']['Graduation'] / self.number_of_samples
    self.Education_Master = sample['Education']['Master'] / self.number_of_samples
    self.Education_PhD = sample['Education']['PhD'] / self.number_of_samples
    self.Marital_Status_Divorced = sample['Marital_Status']['Divorced'] / self.number_of_samples
    self.Marital_Status_Married = sample['Marital_Status']['Married'] / self.number_of_samples
    self.Marital_Status_Single = sample['Marital_Status']['Single'] / self.number_of_samples
    self.Marital_Status_Together = sample['Marital_Status']['Together'] / self.number_of_samples
    self.Income_less_than_30k = sample['Income']['< 30k'] / self.number_of_samples
    self.Income_from_30k_to_50k = sample['Income']['30k_50k'] / self.number_of_samples
    self.Income_from_50k_to_75k = sample['Income']['50k_75k'] / self.number_of_samples
    self.Income_above_75k = sample['Income']['> 75k'] / self.number_of_samples
    self.birth_older_than_1960 = sample['Year_Birth']['< 1960'] / self.number_of_samples
    self.birth_from_1960_to_1975 = sample['Year_Birth']['1960_1975'] / self.number_of_samples
    self.birth_younger_than_1975 = sample['Year_Birth']['> 1975'] / self.number_of_samples


  def get_feature_vector_list(self):
    return [self.year, self.income, self.kids, self.Online_Activity, self.Education_Basic,
            self.Education_Graduation, self.Education_Master, self.Education_PhD, self.Marital_Status_Divorced,
            self.Marital_Status_Married, self.Marital_Status_Single, self.Marital_Status_Together]

  def get_feature_vector_dataFrame(self):
    sample_frame = {
        'Kids': self.kids,
        'Online_Activity': self.Online_Activity,
        'Education_Basic': self.Education_Basic,
        'Education_Graduation': self.Education_Graduation,
        'Education_Master': self.Education_Master,
        'Education_PhD': self.Education_PhD,
        'Marital_Status_Divorced': self.Marital_Status_Divorced,
        'Marital_Status_Married': self.Marital_Status_Married,
        'Marital_Status_Single': self.Marital_Status_Single,
        'Marital_Status_Together': self.Marital_Status_Together,
        'Income_less_than_30k': self.Income_less_than_30k,
        'Income_from_30k_to_50k': self.Income_from_30k_to_50k,
        'Income_from_50k_to_75k': self.Income_from_50k_to_75k,
        'Income_above_75k': self.Income_above_75k,
        'birth_older_than_1960': self.birth_older_than_1960,
        'birth_from_1960_to_1975': self.birth_from_1960_to_1975,
        'birth_younger_than_1975': self.birth_younger_than_1975
    }
    return pd.DataFrame(sample_frame, index=[0])

  @staticmethod
  def get_common_groups():
    groups = []
    education_grp = ['Education_Basic', 'Education_Graduation', 'Education_Master', 'Education_PhD']
    marital_status_grp = [ 'Marital_Status_Divorced', 'Marital_Status_Married', 'Marital_Status_Single', 'Marital_Status_Together']
    birth_date_grp = ['birth_older_than_1960', 'birth_from_1960_to_1975', 'birth_younger_than_1975']
    income_grp = ['Income_less_than_30k', 'Income_from_30k_to_50k', 'Income_from_50k_to_75k', 'Income_above_75k']
    groups.append(education_grp)
    groups.append(marital_status_grp)
    groups.append(birth_date_grp)
    groups.append(income_grp)
    return groups

  @staticmethod
  def get_default_product_profile():
    return {'id': 0,
            'name': 0,
            'no_of_samples':0,
            'Year_Birth': {
                      '< 1960': 0,
                      '1960_1975': 0,
                      '> 1975' : 0
                  },
                  'Education': {
                      'Graduation': 0,
                      'PhD': 0,
                      'Master': 0,
                      'Basic' : 0
                  },
                  'Marital_Status': {
                      'Married': 0,
                      'Together': 0,
                      'Single': 0,
                      'Divorced' : 0
                  },
                  'Income': {
                        '< 30k': 0,
                        '30k_50k': 0,
                        '50k_75k': 0,
                        '> 75k': 0,
                    },
                  'Kids': {
                      'kids': 0,
                      'No Kids': 0
                  },
                'purchase_method':{
                    'Store': 0,
                    'Online': 0
                }}

  @staticmethod
  def generate_output_from_dataframe(output_dataFrame):
    return {'Year_Birth': {
                      '< 1960': output_dataFrame.birth_older_than_1960,
                      '1960_1975': output_dataFrame.birth_from_1960_to_1975,
                      '> 1975' : output_dataFrame.birth_younger_than_1975
                  },
                  'Education': {
                      'Graduation': output_dataFrame.Education_Graduation,
                      'PhD': output_dataFrame.Education_PhD,
                      'Master': output_dataFrame.Education_Master,
                      'Basic' : output_dataFrame.Education_Basic
                  },
                  'Marital_Status': {
                      'Married': output_dataFrame.Marital_Status_Married,
                      'Together': output_dataFrame.Marital_Status_Together,
                      'Single': output_dataFrame.Marital_Status_Single,
                      'Divorced' : output_dataFrame.Marital_Status_Divorced
                  },
                  'Income': {
                        '< 30k': output_dataFrame.Income_less_than_30k,
                        '30k_50k': output_dataFrame.Income_from_30k_to_50k,
                        '50k_75k': output_dataFrame.Income_from_50k_to_75k,
                        '> 75k': output_dataFrame.Income_above_75k,
                    },
                  'Kids': {
                      'kids': output_dataFrame.Kids,
                      'No Kids': 1 - output_dataFrame.Kids
                  },
                'purchase_method':{
                    'Store': 1 - output_dataFrame.Online_Activity,
                    'Online': output_dataFrame.Online_Activity
                }}
  @staticmethod
  def update_product_profile(new_sample, product_profile):
    for field in new_sample:
      if (field == 'name' or field == 'id'):
        continue
      elif (field == 'no_of_samples'):
        product_profile[field] += new_sample[field]
      else:
        for subfield in new_sample[field]:
          product_profile[field][subfield] += (new_sample[field][subfield] * new_sample['no_of_samples'])