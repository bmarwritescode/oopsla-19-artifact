import os
import unittest
import csv
import random

import java_sk.main
import java_sk.util
import re
from . import TestCommon

pwd = os.path.dirname(__file__)
tests = os.path.join(pwd, "..", "benchmarks")

numTests = 31
timeout = 240
seeds = [5620784, 101691, 8252065, 2986398, 3684116, 1936613, 5660081, 1616878, 1242668, 4329362, 4166198, 1743435, 5532210, 3549013, 9580647, 912940, 9786399, 4121193, 5039353, 1481461, 7189671, 1899713, 3415604, 235212, 3395177, 9674824, 9787984, 7445300, 34546, 9402385, 8707669]

class TestJava(TestCommon):
    def __test(self, fs, inline, unroll, adp_conc=False,
               arr=32, cbits=-1, irange=-1, seed=0):
        _fs = map(lambda f: os.path.join(tests, f), fs)
        options = ["--bnd-inline-amnt", str(inline), "--bnd-unroll-amnt", str(unroll), "--bnd-arr-size", str(arr), "--slv-timeout", str(timeout), "--slv-seed", str(seed)] 
        if adp_conc:
            options += ["--slv-randassign", "--slv-simple-inputs"]
        if cbits != -1:
            options += ["--bnd-cbits", str(cbits)]
        if irange != -1:
            options += ["--bnd-int-range", str(irange)]
        ret = java_sk.main.translate(prg=_fs, log_lvl='30', lib=False, opts=options)
        return ret
        
    def run_tests(self, tests, results, tmp_output):
        for k in range(0, len(tests)):
            (t, f) = tests[k]
            results.append([f])
            tmp_output.write(f+"\n")
            tmp_output.flush()
            time_outs = 0
            for i in range(0, numTests):
                result = t(seeds[i])
                try:
                    output = open(os.path.join('result', 'output', '{0}.txt'.format(f)))
                    text = [line for line in output][-1]
                    time = re.match(r'Total time = ([0-9]*)', text, re.M|re.I)
                    time = time.group(1)
                    if int(time) > timeout*60*1000:
                        time_outs += 1
                        time += " (TIMEOUT!)"
                    else if result != 0:
                        time += " (ERROR!)"
                    results[k].append(time)
                    output.close()
                    tmp_output.write(time+"\n")
                    tmp_output.flush()
                    if time_outs >= 5:
                        break
                except:
                    results[k].append("ERROR!")
                    output.close()
            results[k].append('--'*8)
            
        return results

    def test_runMocks(self):
        tmp_output = open(os.path.join('artifact_results', 'full', 'out_mock.txt'), 'w')        
        mockResults = open(os.path.join('artifact_results', 'full', 'results_mock.csv'), 'w')
        writer = csv.writer(mockResults)
        mockTests = [
            (self.run_CipherFactoryMock, 'CipherFactoryTests'),   
            (self.run_SuffixArrayMock, 'SuffixArrayTest'),
            (self.run_HashMap1Mock, 'HashTableTest'),
            (self.run_HashMap2Mock, 'BucketingTest'),
            (self.run_EasyCSVMock, 'CSVTester'),
            (self.run_RomListMock, 'RomListTester'),
            (self.run_ComparatorMock, 'Comparator'),
            (self.run_PasswordManagerMock, 'PasswordManagerTest'),
            (self.run_KafkaMock, 'Kafka_Tester')                
        ]
        results = map(lambda x: [x], reduce(lambda x,y: x + y, self.run_tests(mockTests, [], tmp_output)))
        writer.writerows(results)
        mockResults.close()

    def test_runRewrites(self):
        tmp_output = open(os.path.join('artifact_results', 'full', 'out_rewrite.txt'), 'w')                
        rewriteResults = open(os.path.join('artifact_results', 'full', 'results_rewrite.csv'), 'w')
        writer = csv.writer(rewriteResults)
        rewriteTests = [
            (self.run_CipherFactoryRewrite, 'CipherFactoryTests'),
            (self.run_SuffixArrayRewrite, 'SuffixArrayTest'),
            (self.run_HashMap1Rewrite, 'HashTableTest'),
            (self.run_HashMap2Rewrite, 'BucketingTest'),
            (self.run_EasyCSVRewrite, 'CSVTester'),
            (self.run_RomListRewrite, 'RomListTester'),
            (self.run_ComparatorRewrite, 'Comparator'),
            (self.run_PasswordManagerRewrite, 'PasswordManagerTest'),
            (self.run_KafkaRewrite, 'Kafka_Tester')
        ]
        results = map(lambda x: [x], reduce(lambda x,y: x + y, self.run_tests(rewriteTests, [], tmp_output)))
        writer.writerows(results)
        tmp_output.close()
        rewriteResults.close()
        
    def run_SuffixArrayMock(self, seed):
        files = ["SuffixArray_syn.java", "SuffixArrayTest.java", "mock/", "shared/"]
        files = map(lambda s: "SuffixArray/" + s, files)
        inline = 3
        unroll = 8
        return self.__test(files, inline, unroll, seed=seed)
        
    def run_SuffixArrayRewrite(self, seed):
        files = ["SuffixArray_syn.java", "SuffixArrayTest.java", "rewrite/", "shared/"]        
        files = map(lambda s: "SuffixArray/" + s, files)
        inline = 3
        unroll = 8
        return self.__test(files, inline, unroll, seed=seed)
        
    def run_HashMap1Mock(self, seed):
        files = ["HashTable_syn.java", "HashTableNode.java", "HashTableTest.java", "mock/", "shared/"]
        files = map(lambda s: "HashMap1/" + s, files)
        inline = 1
        unroll = 5
        return self.__test(files, inline, unroll, seed=seed, irange=43)
        
    def run_HashMap1Rewrite(self, seed):
        files = ["HashTable_syn.java", "HashTableNode.java", "HashTableTest.java", "rewrite/", "shared/"]
        files = map(lambda s: "HashMap1/" + s, files)
        inline = 1
        unroll = 5
        return self.__test(files, inline, unroll, seed=seed, irange=43)

    def run_HashMap2Mock(self, seed):
        files = ["Bucketing_syn.java", "BucketingTest.java", "HashTable.java", "Pair.java", "mock/", "shared/"]
        files = map(lambda s: "HashMap2/" + s, files)
        inline = 1
        unroll = 2
        return self.__test(files, inline, unroll, seed=seed, cbits=2, irange=8)
        
    def run_HashMap2Rewrite(self, seed):
        files = ["Bucketing_syn.java", "BucketingTest.java", "HashTable.java", "Pair.java", "rewrite/", "shared/"]
        files = map(lambda s: "HashMap2/" + s, files)
        inline = 1
        unroll = 2
        return self.__test(files, inline, unroll, seed=seed, cbits=2, irange=8)

    def run_EasyCSVMock(self, seed):
        files = ["CsvDocument_syn.java", "CodeAssertion.java", "CsvColumn.java", "CsvColumnTest.java", "CsvConfiguration.java", "CsvDocumentTest.java", "CsvRow.java", "CsvRowTest.java", "Tester.java", "mock/", "shared/"]
        files = map(lambda s: "EasyCSV/" + s, files)
        inline = 3
        unroll = 5
        return self.__test(files, inline, unroll, seed=seed)

    def run_EasyCSVRewrite(self, seed):
        files = ["CsvDocument_syn.java", "CodeAssertion.java", "CsvColumn.java", "CsvColumnTest.java", "CsvConfiguration.java", "CsvDocumentTest.java", "CsvRow.java", "CsvRowTest.java", "Tester.java", "rewrite/", "shared/"]
        files = map(lambda s: "EasyCSV/" + s, files)
        inline = 3
        unroll = 5
        return self.__test(files, inline, unroll, seed=seed)

    def run_RomListMock(self, seed):
        files = ["RomlistParser_syn.java", "RomlistGame.java", "Tester.java", "mock/", "shared/"]
        files = map(lambda s: "RomList/" + s, files)
        inline = 2
        unroll = 26
        return self.__test(files, inline, unroll, seed=seed, adp_conc=True)

    def run_RomListRewrite(self, seed):
        files = ["RomlistParser_syn.java", "RomlistGame.java", "Tester.java", "rewrite/", "shared/"]
        files = map(lambda s: "RomList/" + s, files)
        inline = 2
        unroll = 26
        return self.__test(files, inline, unroll, seed=seed, adp_conc=True)

    def run_ComparatorMock(self, seed):
        files = ["CommunicationWithFiles_syn.java", "Comparator.java", "Tester.java", "mock/", "shared/"] 
        files = map(lambda s: "Comparator/" + s, files)
        inline = 2
        unroll = 10
        return self.__test(files, inline, unroll, seed=seed, adp_conc=True)

    def run_ComparatorRewrite(self, seed):
        files = ["CommunicationWithFiles_syn.java", "Comparator.java", "Tester.java", "rewrite/", "shared/"] 
        files = map(lambda s: "Comparator/" + s, files)
        inline = 2
        unroll = 10
        return self.__test(files, inline, unroll, seed=seed, adp_conc=True)

    def run_PasswordManagerMock(self, seed):
        files = ["Cryptographer_syn_mock.java", "PasswordManager.java", "PasswordMap.java", "PasswordManagerTest.java", "mock/", "shared/"]
        files = map(lambda s: "PasswordManager/" + s, files)
        inline = 2
        unroll = 16
        return self.__test(files, inline, unroll, seed=seed)
        
    def run_PasswordManagerRewrite(self, seed):
        files = ["Cryptographer_syn_rewrite.java", "PasswordManager.java", "PasswordMap.java", "PasswordManagerTest.java", "rewrite/", "shared/"]
        files = map(lambda s: "PasswordManager/" + s, files)
        inline = 2
        unroll = 16
        return self.__test(files, inline, unroll, seed=seed)
        
    def run_CipherFactoryMock(self, seed):
        files = ["CryptoManager_syn_mock.java", "CipherFactoryTester.java", "ConfigurableCipherFactory.java", "DefaultCipherFactory_mock.java", "ICipherFactory.java", "ICryptoManager.java", "mock/", "shared/"]
        files = map(lambda s: "CipherFactory/" + s, files)
        inline = 3
        unroll = 9
        return self.__test(files, inline, unroll, seed=seed)
    
    def run_CipherFactoryRewrite(self, seed):
        files = ["CryptoManager_syn_rewrite.java", "CipherFactoryTester.java", "ConfigurableCipherFactory.java", "DefaultCipherFactory_rewrite.java", "ICipherFactory.java", "ICryptoManager.java", "rewrite/util", "rewrite/security","rewrite/crypto", "shared/"]
        files = map(lambda s: "CipherFactory/" + s, files)
        inline = 3
        unroll = 9
        return self.__test(files, inline, unroll, seed=seed)

    def run_KafkaMock(self, seed):
        files = ["OpenSSLCipher_syn_mock.java", "CipherFactory.java", "ICipher_mock.java", "Tester_mock.java", "mock/", "shared/"]
        files = map(lambda s: "Kafka/" + s, files)
        inline = 2
        unroll = 35
        return self.__test(files, inline, unroll, seed=seed)

    def run_KafkaRewrite(self, seed):
        files = ["OpenSSLCipher_syn_rewrite.java", "CipherFactory.java", "ICipher_rewrite.java", "Tester_rewrite.java", "rewrite/", "shared/"]
        files = map(lambda s: "Kafka/" + s, files)
        inline = 2
        unroll = 35
        return self.__test(files, inline, unroll, seed=seed)        

if __name__ == '__main__':
  unittest.main()
        
