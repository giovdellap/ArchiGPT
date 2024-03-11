from .parser import InputSampleParser
from sklearn.neighbors import NearestNeighbors
import pandas as pd

class KnnAnalyzer:
  def __init__(self, n_neighbors, dataset):
    self.n_neighbors = n_neighbors
    self.dataset = dataset
    self.model = NearestNeighbors(n_neighbors = self.n_neighbors)
    self.model.fit(dataset)

  def _inferSample(self, sample):
    self.sample = InputSampleParser(sample).get_feature_vector_dataFrame()
    distances, neighbours_indicies =  self.model.kneighbors(self.sample)
    return distances, neighbours_indicies

  def _processOutput(self, distances, neighbours_indicies):
    # construct the result from weighted negibours
    output_dataFrame = self.dataset.iloc[0].copy()
    output_dataFrame[:] = 0
    max_distance = max(distances)
    min_distance = min(distances)
    for column in self.dataset.columns:
      weighted_sum = 0.0
      for index in range(len(neighbours_indicies)):
        neighbour_similarity = 1 - ((distances[index] - min_distance) / (max_distance - min_distance))
        weighted_sum += neighbour_similarity * float(self.dataset[column].iloc[neighbours_indicies[index]])
      output_dataFrame[column] = (weighted_sum/self.n_neighbors)
    return output_dataFrame

  def _generateOutput(self, output_dataFrame):
    return InputSampleParser.generate_output_from_dataframe(self.output_dataFrame)

  def _convert_values_to_percentages(self, output_dataFrame):
    groups = InputSampleParser.get_common_groups()
    for group in groups:
      sum = output_dataFrame[group].sum()
      for field in group:
        output_dataFrame[field] = output_dataFrame[field]/sum
    return output_dataFrame

  def describeNeighbours(self):
    return self.dataset.iloc[self.neighbours_indicies].describe()
  
  def infer(self,sample):
    # infer Knn
    distances, neighbours_indicies = self._inferSample(sample)
    self.distances = distances[0]
    self.neighbours_indicies= neighbours_indicies[0]

    #build results from neighbours
    self.output_dataFrame = self._processOutput(self.distances, self.neighbours_indicies)

    #convert values to percentages
    self.output_dataFrame = self._convert_values_to_percentages(self.output_dataFrame)

    #generate output object from parser
    self.output_result = self._generateOutput(self.output_dataFrame)

    return self.output_result