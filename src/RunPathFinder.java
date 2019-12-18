

public class RunPathFinder {    

    private static void testGridGraph(GridGraph graph, String algorithm, String start, String goal) {
        PathFinder<GridGraph.Coord> finder = new PathFinder<>(graph);
        String[] startC = start.split(":");
        String[] goalC = goal.split(":");
        PathFinder<GridGraph.Coord>.Result<GridGraph.Coord> result =
            finder.search(algorithm,
                          new GridGraph.Coord(Integer.valueOf(startC[0]), Integer.valueOf(startC[1])),
                          new GridGraph.Coord(Integer.valueOf(goalC[0]), Integer.valueOf(goalC[1])));
        if (result.success && graph.width() < 250 && graph.height() < 250)
            System.out.println(graph.showGrid(result.path));
        System.out.println(result);
    }


    public static void main(String[] args) {
        try {
            //---run from command line---
            //if (args.length != 5) throw new IllegalArgumentException();

            //---task 1: Dijkstra----
            //String algorithm = "dijkstra", graphType = "AdjacencyGraph", filePath = "src/graphs/AdjacencyGraph/citygraph-VGregion.txt", start = "Göteborg", goal = "Götene"; //158.0
            //String algorithm = "dijkstra", graphType = "AdjacencyGraph", filePath = "src/graphs/AdjacencyGraph/citygraph-SE.txt", start = "Lund", goal = "Kiruna"; //1826.0
            //String algorithm = "dijkstra", graphType = "NPuzzle", filePath = "3", start = "/_AB/CDE/FGH/", goal = "/CBA/DEF/_HG/"; //22.0
            // String algorithm = "dijkstra", graphType = "NPuzzle", filePath = "3", start = "/_AB/CDE/FGH/", goal = "/ABC/DEF/GH_/"; //22.0
            //String algorithm = "dijkstra", graphType = "GridGraph", filePath = "src/graphs/GridGraph/maze-100x50.txt", start = "1:1", goal = "199:99"; //9703 : 1216.4793641885828

            //---Task 2: Dijskstra + WordLadder---
            //String algorithm = "dijkstra", graphType = "WordLadder", filePath = "src/graphs/WordLadder/words-romaner.txt", start = "mamma", goal = "pappa"; // 338 : 6.0
            //String algorithm = "dijkstra", graphType = "WordLadder", filePath = "src/graphs/WordLadder/words-romaner.txt", start = "katter", goal = "hundar";  //2350 : 14.0
            //String algorithm = "dijkstra", graphType = "WordLadder", filePath = "src/graphs/WordLadder/words-romaner.txt", start = "örter", goal = "öring";   //4409 : 30.0


            //--- Task 3: astar---
            // String algorithm = "dijkstra", graphType="NPuzzle", filePath="3", start="/CBA/DEF/_HG/", goal="/ABC/DEF/GH_/"; //130589 : 14.0
            // String algorithm = "astar", graphType="NPuzzle", filePath="3", start="/CBA/DEF/_HG/", goal="/ABC/DEF/GH_/";   //3720: 14.0


            //---Task 4: astar + guessCost----
            //String algorithm = "dijkstra", graphType="GridGraph", filePath="src/graphs/GridGraph/AR0012SR.map", start="11:73", goal="85:127";  //5654 : 147..
            //String algorithm = "astar", graphType="GridGraph", filePath="src/graphs/GridGraph/AR0012SR.map", start="11:73", goal="85:127";   //2537 : 147...
            //String algorithm = "dijkstra", graphType="WordLadder", filePath="src/graphs/WordLadder/words-saldo.txt", start="eller", goal="glada"; // 5984:7
            String algorithm = "astar", graphType="WordLadder", filePath="src/graphs/WordLadder/words-saldo.txt", start="eller", goal="glada"; //100 : 7.0



            PathFinder<String> finder;
            switch (graphType) {

            case "AdjacencyGraph":
                finder = new PathFinder<>(new AdjacencyGraph(filePath));
                System.out.println(finder.search(algorithm, start, goal));
                break;

            case "WordLadder":
                finder = new PathFinder<>(new WordLadder(filePath));
                System.out.println(finder.search(algorithm, start, goal));
                break;

            case "NPuzzle":
                finder = new PathFinder<>(new NPuzzle(Integer.valueOf(filePath)));
                System.out.println(finder.search(algorithm, start, goal));
                break;

            case "GridGraph":
                testGridGraph(new GridGraph(filePath), algorithm, start, goal);
                break;

            default:
                throw new IllegalArgumentException("Unknown graph type: " + graphType);
            }

        } catch (Exception e) {
            // If there is an error, print it and a little command-line help
            e.printStackTrace();
            System.err.println();
            System.err.println("Usage: java RunPathFinder algorithm graphtype graph start goal");
            System.err.println("  where algorithm = random | dijkstra | astar");
            System.err.println("        graphtype = AdjacencyGraph | WordLadder | NPuzzle | GridGraph");
            System.exit(1);
        }
    }

}

