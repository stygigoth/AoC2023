#!/usr/bin/perl

use strict;
use warnings;

my @lines = ();
while (<>) {
  chomp;
  push(@lines,$_);
}


sub part_one {
  my @times = split(' ', $_[0]);
  my @distances = split(' ', $_[1]);
  my %races = ();
  for (my $i = 0; $i <= $#times; $i++) {
    $races{$times[$i]} = $distances[$i];
  }

  my $a = 1;
  for(keys %races) {
      my $sum = 0;
      for my $i (0..$_) {
        if (($_ - $i) * $i > $races{$_}) {
          $sum += 1;
        }
      }
      if ($sum > 0) {
        $a *= $sum;
      }
  }

  print "Part One: ${a}\n"
}

sub part_two {
  my $time = $_[0];
  $time =~ s/\s+//g;
  my $distance = $_[1];
  $distance =~ s/\s+//g;
  
  my $sum = 0;
  for my $i (0..$time) {
    if (($time - $i) * $i > $distance) {
      $sum += 1;
    }
  }

  print "Part Two: ${sum}\n"
}

part_one(@lines);
part_two(@lines);
