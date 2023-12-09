use strict;
use warnings;
use List::Util qw(notall);

sub part_one {
  my @lines;

  while (<>) {
    chomp;
    push(@lines, $_);
  }
  
  my $sum = 0;
  
  foreach my $line (@lines) {
    my @ns;
    my @n = split("\ ", $line);
    my $a = 0;
    while (notall { $_ == 0 } @n) {
      my @na;
      my @it = (1..(scalar(@n) - 1));
      for my $i (@it) {
        push(@na, $n[$i] - $n[$i - 1]);
      }
      push(@ns, $n[$#n]);
      @n = ();
      for my $item (@na) {
        push(@n, $item);
      }
    }
    for my $item (@ns) {
      $sum += $item;
    }
  }
  print("Part one: ", $sum, "\n");
}

sub part_two {
  my @lines;

  while (<>) {
    chomp;
    push(@lines, $_);
  }
  
  my $acc = 0;
  
  foreach my $line (@lines) {
    my $sum = 0;
    my @ns;
    my @n = split("\ ", $line);
    my $a = 0;
    while (notall { $_ == 0 } @n) {
      my @na;
      my @it = (1..(scalar(@n) - 1));
      for my $i (reverse @it) {
        push(@na, $n[$i] - $n[$i - 1]);
      }
      push(@ns, $n[0]);
      @n = ();
      for my $item (reverse @na) {
        push(@n, $item);
      }
    }
    push(@ns, $n[0]);
    @n = ();
    for my $item (reverse @ns) {
      $sum = $item - $sum;
    }
    $acc += $sum;
  }
  print("Part two: ", $acc, "\n");
}

# part_one();
# part_two();
