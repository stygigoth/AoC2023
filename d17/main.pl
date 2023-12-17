use strict;
use warnings;
use List::Util qw(sum);
use Array::Heap::PriorityQueue::Numeric;

my @map = map { chomp; [ split // ] } <>;

my $height = @map;
my $width = @{$map[0]};

my %dirs = (
    '>' => [ 1, 0 ],
    '<' => [ -1, 0 ],
    '^' => [ 0, -1 ],
    'v' => [ 0, 1 ],
);
my %turns = (
    '>' => [ '^', 'v' ],
    '<' => [ '^', 'v' ],
    '^' => [ '<', '>' ],
    'v' => [ '<', '>' ],
    ' ' => [ '<', '>', '^', 'v' ],
);

# my $part = 1;
my $part = 2;
my @moverange = $part == 1 ? (1..3) : (4..10);
my $init = [ 0, 0, 0, ' ', undef ];
my ($exitX, $exitY) = ($width-1, $height-1);
my $pq = Array::Heap::PriorityQueue::Numeric->new();
my @bestHeatLossFor;
$pq->add($init, 0);
my $best = ~0;
my $bestEntry;
while (my $entry = $pq->get()) {
    my ($heatLoss, $x, $y, $lastmove, $seq) = @$entry;
    my $manhattan = abs($x - $exitX) + abs($y - $exitY);
    next if defined $bestHeatLossFor[$y][$x]{$lastmove} and $bestHeatLossFor[$y][$x]{$lastmove} <= $heatLoss;
    $bestHeatLossFor[$y][$x]{$lastmove} = $heatLoss;
    if ($manhattan == 0) {
        next if $heatLoss >= $best;
        $best = $heatLoss;
        $bestEntry = $entry;
        last if keys %{$bestHeatLossFor[$y][$x]} == 2;
    }
    foreach my $next (@{$turns{$lastmove}}) {
        my ($dx, $dy) = @{$dirs{$next}};
        foreach my $movelen (@moverange) {
            my $newx = $x + $dx * $movelen;
            my $newy = $y + $dy * $movelen;
            next if $newx < 0 or $newx >= $width or $newy < 0 or $newy >= $height;
            my $newHeatLoss = $heatLoss + sum map { $map[$y + $dy * $_][$x + $dx * $_] } (1..$movelen);
            $pq->add([$newHeatLoss, $newx, $newy, $next, $entry], $newHeatLoss + abs($newx - $exitX) + abs($newy - $exitY));
        }
    }
}

my @moves;
while ($bestEntry) {
    unshift @moves, $bestEntry;
    my ($heatLoss, $x, $y, $lastmove, $prev) = @$bestEntry;
    $bestEntry = $prev;
}
my ($lastX, $lastY) = (0,0);
shift @moves;
foreach my $entry (@moves) {
    my ($heatLoss, $x, $y, $lastmove, $prev) = @$entry;
    my $len = abs($x - $lastX) + abs($y - $lastY);
    my $dx = ($x - $lastX) / $len;
    my $dy = ($y - $lastY) / $len;
    $map[$lastY + $dy * $_][$lastX + $dx * $_] = $lastmove foreach (1..$len);
    ($lastX, $lastY) = ($x, $y);
}

print "$_\n" foreach map { (join '', @$_) =~ s/(\D+)/\e[46m$1\e[0m/gr; } @map;

print "Best: $best\n";

