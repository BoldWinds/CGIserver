import sys

if __name__ == '__main__':
    if len(sys.argv) != 3:
        print("Please provide two numbers as arguments.")
    else:
        num1 = int(sys.argv[1])
        num2 = int(sys.argv[2])
        sum = num1 + num2
        product = num1 * num2
        print ("<html>\n")
        print ("<h1>Calculator Result:</h1>\n")
        print (f"<p>Number 1: {num1}</p>\n")
        print (f"<p>Number 2: {num2}</p>\n")
        print (f"<p>Product: {product}</p>\n")
        print (f"<p>Sum: {sum}</p>\n")
        print ("</html>\n")
