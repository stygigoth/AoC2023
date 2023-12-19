use strict;
use warnings;
use List::Util qw(min max sum product);

$/ = '';
my @chunks = <>;
chomp @chunks;

my @data1 = split /\n/, shift @chunks;

my %rules;
foreach (@data1) {
    my ($name, $rules) = m/^(\w+)\{(.*)\}\z/ or die;
    my @rules = split /,/, $rules;
    my $final = pop @rules;
    my @lines = map { my ($cond, $dest) = m/^(.*):(.*)\z/ or die;
                      my ($var, $op, $val) = $cond =~ m/^(\w)([<>])(\d+)\z/ or die;
                      [$var, $op, $val, $dest] } @rules;
    push @lines, $final;
    $rules{$name} = \@lines;
}

my @ranges = ( ['in', [1, 4000], [1, 4000], [1, 4000], [1, 4000]] );
my %indexForVar = (x => 0, m => 1, a => 2, s => 3);
my ($total, $count, $breadth) = (0, 0, 0);

OUTER: while (@ranges) {
    $breadth = max $breadth, scalar(@ranges);
    my ($name, @xmas) = @{shift @ranges};
    if ($name eq 'A') {
        # addToTotal(\@xmas);
        $total += product map { $_->[1] - $_->[0] + 1 } @xmas;
        ++$count;
        next;
    } elsif ($name eq 'R') {
        next;
    }
    my @rules = @{$rules{$name}};
    my $final = pop @rules;
    foreach my $rule (@rules) {
        my ($var, $op, $val, $dest) = @$rule;
        my $index = $indexForVar{$var};
        my @xmasCopy = map { [@$_] } @xmas;
        if ($op eq '<') {
            $xmasCopy[$index][1] = min $xmasCopy[$index][1], $val-1;
            $xmas[$index][0] = max $xmas[$index][0], $val;
        } elsif ($op eq '>') {
            $xmasCopy[$index][0] = max $xmasCopy[$index][0], $val+1;
            $xmas[$index][1] = min $xmas[$index][1], $val;
        } else {
            die;
        }
        unless ($xmasCopy[$index][0] > $xmasCopy[$index][1]) {
            unshift @ranges, [$dest, @xmasCopy];
        }
        next OUTER if $xmas[$index][0] > $xmas[$index][1];
    }
    unshift @ranges, [$final, @xmas];
}

print "Part two: $total ($count ranges, $breadth states at once)\n";
