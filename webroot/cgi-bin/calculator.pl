#!/usr/bin/perl

use strict;
use warnings;
use CGI;

# Create a CGI object
my $cgi = CGI->new;

# Get the values of the input fields
my $num1 = shift @ARGV;
my $num2 = shift @ARGV;

# Calculate the product and sum
my $product = $num1 * $num2;
my $sum = $num1 + $num2;

# Generate the HTML response
# print $cgi->header;
print $cgi->start_html("Calculator Result");
print "<h1>Calculator Result:</h1>\n";
print "<p>Number 1: $num1</p>\n";
print "<p>Number 2: $num2</p>\n";
print "<p>Product: $product</p>\n";
print "<p>Sum: $sum</p>\n";
print $cgi->end_html;
