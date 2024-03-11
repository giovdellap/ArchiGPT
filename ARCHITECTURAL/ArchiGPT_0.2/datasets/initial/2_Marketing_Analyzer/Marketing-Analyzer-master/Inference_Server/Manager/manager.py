from cmath import inf
from Dao import databaseCluster
from .Inferer import KnnAnalyzer

import pandas as pd

class Manager:
    def __init__(self, databaseConfig):
        try:
            self.dataset = None
            self.datasetVersion = -inf
            self.number_of_neighbours = 1200
            self.uri = databaseConfig['uri']
            self.databaseName = databaseConfig['database_name']
            self.dataCollection = databaseConfig['dataset_collection']
            self.configCollection = databaseConfig['confing_collection']
            self.cluster = databaseCluster(self.uri)
        except Exception as err:
            raise Exception(err)

    def _setDatabase(self,database, collectionName):
        try:
            self.cluster.init(database, collectionName)
        except Exception as err:
            raise Exception(err)

    def _getAllDocs(self):
        try:
            return self.cluster.getAllDocs()
        except Exception as err:
            raise Exception(err)

    def _getDatasetVersion(self):
        self._setDatabase(self.databaseName, self.configCollection)
        for document in self._getAllDocs():
            return document['version']
    
    def _updateDataset(self):
        self._setDatabase(self.databaseName, self.dataCollection)
        results = []
        for result in self._getAllDocs():
            result.pop("_id",None)
            result.pop("",None)
            results.append(result)
        self.dataset = pd.DataFrame(results)

    def InferSample(self, sample):
        currentVersion = self._getDatasetVersion()
        if (self.datasetVersion != currentVersion):
            self._updateDataset()
            self.datasetVersion = currentVersion
        
        analyzer = KnnAnalyzer(self.number_of_neighbours, self.dataset)
        result = analyzer.infer(sample)
        return result