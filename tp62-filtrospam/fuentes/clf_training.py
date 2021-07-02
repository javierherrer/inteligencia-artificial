# Classifier training with Enron
# Javier Herrer Torres
# Ene-2021

######################################################
# Imports
######################################################

import glob
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.naive_bayes import MultinomialNB
from sklearn.naive_bayes import BernoulliNB
from sklearn.utils import shuffle
import time

from sklearn.model_selection import KFold, cross_val_score


######################################################
# Functions for loading mails
######################################################

def read_folder(folder):
    mails = []
    file_list = glob.glob(folder)  # List mails in folder
    num_files = len(file_list)
    for i in range(0, num_files):
        i_path = file_list[i]
        # print(i_path)
        i_file = open(i_path, 'rb')
        i_str = i_file.read()
        i_text = i_str.decode('utf-8', errors='ignore')  # Convert to Unicode
        mails.append(i_text)  # Append to the mail structure
        i_file.close()
    return mails


def load_enron_folders(datasets):
    path = r'C:\Users\JAVIER\Desktop\Practicas\IA\FiltroSpam\datasets\\'
    ham = []
    spam = []
    for j in datasets:
        ham  = ham  + read_folder(path + 'enron' + str(j) + '\ham\*.txt')
        spam = spam + read_folder(path + 'enron' + str(j) + '\spam\*.txt')
    num_ham  = len(ham)
    num_spam = len(spam)
    print("mails:", num_ham+num_spam)
    print("ham  :", num_ham)
    print("spam :", num_spam)

    mails = ham + spam
    labels = [0]*num_ham + [1]*num_spam
    mails, labels = shuffle(mails, labels, random_state=0)
    return mails, labels

######################################################
# Main
######################################################
print("Loading files...")

print("------Loading train and validation data--------")
mails, y = load_enron_folders([1,2,3,4,5])

print("--------------Loading Test data----------------")
mails_test, y_test = load_enron_folders([6])


classifiers = [MultinomialNB(), BernoulliNB()]
clf_names = ["MultinomialNB", "BernoulliNB"]
vectorizers = [CountVectorizer(), TfidfVectorizer()]
vec_names = ["CountVectorizer", "TfidfVectorizer"]
laplace = [0.1, 0.25, 0.5, 0.75, 1]
ngrams = [(1,1), (1,2)]
ngrams_names = ["Unigrams", "Unigrams and bigrams"]
scores = ["accuracy", "f1"]

k_fold = KFold(n_splits=10, shuffle=True, random_state=0)
for clf, clf_name in zip(classifiers, clf_names):
    
    for vec, vec_name in zip(vectorizers, vec_names):
        
        X = vec.fit_transform(mails)
        X_test = vec.transform(mails_test)
        
        for k in laplace:
            clf_laplace = clf.set_params(alpha = k)
            
            for ngram, ngram_name in zip(ngrams, ngrams_names):
                vec.set_params(ngram_range = ngram)
        
                start = time.time()            
                print ("------------------------")
                print ("Classifier: ", clf_name)
                print ("Vectorizer: ", vec_name)
                print ("Laplace:    ", k)
                print ("Ngram:      ", ngram_name)
                for score in scores:
                    print ("%-10s %s" %(score, 
                                        cross_val_score(clf_laplace, X, y, 
                                                        scoring=score, 
                                                        cv=k_fold).mean()))
                
                print ("Time : ", time.time() - start, "\n")


        