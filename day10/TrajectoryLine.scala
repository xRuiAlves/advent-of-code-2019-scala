package day10

class TrajectoryLine(var x: Int, var y: Int) {
    (x, y) match {
        case (0, _) => y = 1 * math.signum(y)
        case (_, 0) => x = 1 * math.signum(x)
        case (x, y) => {
            var divisor = gcd(x, y)
            this.x /= divisor
            this.y /= divisor
        }
    }

    private def gcd(a: Int, b: Int): Int = {
        for (i <- math.min(math.abs(x), math.abs(y)) to 2 by -1) {
            if ((a % i == 0) && (b % i == 0)) return i
        }
        1
    }

    override def toString: String = s"$x/$y"

    override def hashCode(): Int = ((x*1000 + y << 2) / 7) % 13

    override def equals(obj: Any): Boolean = obj match {
        case obj: TrajectoryLine => obj.x == x && obj.y == y
        case _ => false
    }
}
