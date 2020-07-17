


def main():
    f_dict = open("blends.txt", "r")
    line = f_dict.readline()
    #.split("\n")[0]
    line=line.split("\t")[0]
    fo=open("1.txt","w")
    while line:
        
        fo.write(line+"\n")
        

        
        line = f_dict.readline()
        
        line = line.split("\t")[0]
    f_dict.close()
    fo.close()

    return

if __name__=="__main__":
    main()