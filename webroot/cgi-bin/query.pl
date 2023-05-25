#!/usr/bin/perl
use strict;
use warnings;
use CGI;
use DBI;

# Connect to the MySQL database
my $dbh = DBI->connect("DBI:mysql:database=DB_NAME;host=DB_HOST;port=DB_PORT", "DB_USER", "DB_PASSWORD")
  or die "Could not connect to database: $DBI::errstr";

# Create a CGI object
my $cgi = CGI->new;

# Retrieve the student ID from the form submission
my $student_id = shift @ARGV;

# Query the database for the student's information
my $query = "SELECT StudentID, StudentName, Class FROM Students WHERE StudentID = ?";
my $sth = $dbh->prepare($query);
$sth->execute($student_id);

# Fetch the results
my ($found_student_id, $student_name, $class) = $sth->fetchrow_array;

# Output the results
print $cgi->header('text/html');


# Generate the HTML response
# print $cgi->header;
print $cgi->start_html("Query Result");
print "<h1>Query Result:</h1>\n";
print "<p>n";
if ($found_student_id) {
  print "Student ID: $found_student_id<br>\n";
  print "Student Name: $student_name<br>\n";
  print "Class: $class<br>\n";
} else {
  print "Student not found\n";
}
print "</p>\n";
print $cgi->end_html;

# Disconnect from the database
$dbh->disconnect;
