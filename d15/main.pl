use strict;
use warnings;
use List::Util qw(sum);
use List::MoreUtils qw(firstidx);

sub hash ($) {
    my $data = shift;
    my $sum = 0;
    foreach my $char (split //, $data) {
        $sum += ord($char);
        $sum *= 17;
        $sum %= 256;
    }
    return $sum;
}

$_ = <>;
chomp;
my @steps = split /,/;
my $sum = sum map { hash($_) } @steps;
print "Part 1: $sum\n\n";

# Part 2
my @table = map { [] } 0..255;
foreach my $step (@steps) {
    my ($label, $op, $focal) = $step =~ m/(\w+)([-=])(\d*)/ or die;
    my $hash = hash($label);
    my $list = $table[$hash];
    if ($op eq '-') {
        my $subsequent;
        @$list = grep { $_->[0] ne $label || $subsequent++ } @$list;
    } else {
        my $location = firstidx { $_->[0] eq $label } @$list;
        if ($location >= 0) {
            $list->[$location][1] = $focal;
        } else {
            push @$list, [$label, $focal];
        }
    }
}

my $power = 0;
foreach my $boxId (0..255) {
    my $list = $table[$boxId];
    foreach my $slot (0..$#$list) {
        my $boxNo = $boxId + 1;
        my $slotNo = $slot + 1;
        my $focal = $list->[$slot][1];
        $power += $boxNo * $slotNo * $focal;
    }
}

print "Part 2: $power\n";

