package software.aoc.day8.a;

import java.util.Map;

public record JunctionBox(int id, long x, long y, long z, int parentId, int circuitSize) {
}
