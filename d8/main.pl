use strict;
use warnings;

$/ = '';
my @chunks = <>;
chomp @chunks;

my $directions = shift @chunks;
chomp $directions;
my @directions = split //, $directions;

my @edgelist = split /\n/, shift @chunks;

my %edges;  
my @curr;

foreach (@edgelist) {
    my ($src, $left, $right) = m/^(\S+)\s*=\s*\((\S+), (\S+)\)/;
    $edges{"$src,L"} = $left;
    $edges{"$src,R"} = $right;
    push @curr, $src if $src =~ m/A\z/;
}


if (0) {
    # Part 1
    my $curr = 'AAA';
    my $steps = 0;
    while ($curr ne 'ZZZ') {
        ++$steps;
        my $dir = shift @directions;
        push @directions, $dir;
        $curr = $edges{"$curr,$dir"};
    }
    exit;
}

# Part 2

print "Starts: @curr\n";
my @loops;
foreach my $start (@curr) {
    my $steps = 0;
    my %seen;
    my %ends;
    my $curr = $start;
    my @dirs = @directions;
    my $dirindex = 0;
    while (!defined $seen{$curr,$dirindex}) {
        $seen{$curr,$dirindex} = $steps;
        if ($curr =~ m/Z\z/) {
            $ends{$curr} = $steps;
        }
        $dirindex = ($dirindex + 1) % @dirs;
        ++$steps;
        my $dir = shift @dirs;
        push @dirs, $dir;
        $curr = $edges{"$curr,$dir"};
    }
    my $cyclelen = $steps - $seen{$curr,$dirindex};
    print "Start: $start, ends: ", join(", ", %ends), " (cycle length: $cyclelen)\n";
    my @looplens = values %ends;
    push @loops, $cyclelen;
}

sub lcm($$) {
    my ($a, $b) = @_;
    return $a * $b / gcd($a, $b);
}

sub gcd($$);
sub gcd($$) {
    my ($a, $b) = @_;
    return $a if $b == 0;
    return gcd($b, $a % $b);
}

sub lcm_list (@) {
    my $lcm = shift;
    foreach my $n (@_) {
        $lcm = lcm($lcm, $n);
    }
    return $lcm;
}

my $totalsteps = lcm_list(@loops);

print "Total steps: $totalsteps\n";

