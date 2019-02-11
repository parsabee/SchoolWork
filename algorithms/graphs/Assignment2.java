import java.util.Scanner;

public class Assignment2 {
	public static void main(String[] args){
		int num_graphs, num_nodes, num_edges; 
		Scanner scan; 
		
		scan = new Scanner(System.in);
		num_graphs = scan.nextInt();
		for(int x=1; x<= num_graphs; x++){
			System.out.println("graph number: "+ x);
			num_nodes = scan.nextInt();
			num_edges = scan.nextInt();
			int [][] adjMatrix = new int [num_nodes+1][num_nodes+1];
			for(int j=1; j<=num_edges; j++){
				int v= scan.nextInt();
				int u= scan.nextInt();
				adjMatrix[v][u] = 1;
			}

			int [] shortest_distances = new int[num_nodes+1];
			shortest_distances[1]=0;
			for(int i=2; i<=num_nodes; i++){
				shortest_distances[i]=Integer.MAX_VALUE;
			}
			
			for(int i=1; i<=num_nodes; i++){
				for(int j=1; j<=num_nodes;j++){
					if(adjMatrix[i][j]==1){
						if((shortest_distances[i]+1)<shortest_distances[j]){
							shortest_distances[j]=shortest_distances[i]+1;
						}
					}
				}
			}

			System.out.printf("Shortest path: %d\n", shortest_distances[num_nodes]);


			int [] longest_distances = new int[num_nodes+1];
			longest_distances[1]=0;

			for(int i=2; i<=num_nodes; i++){
				longest_distances[i]=Integer.MIN_VALUE;
			}

			for(int i=1; i<=num_nodes; i++){
				for(int j=1; j<=num_nodes;j++){	
					if(adjMatrix[i][j]==1){
						if(i!=j){
							if((longest_distances[i]+1) > longest_distances[j]){
								longest_distances[j] = longest_distances[i]+1;
							}
						}
					}		
				}
			}

			System.out.printf("Longest path: %d\n", longest_distances[num_nodes]);

			int [] paths = new int[num_nodes+1];
			paths[1] = 1;
			for(int i=2; i<=num_nodes; i++){
				paths[i]= 0;
			}
			
			for(int i=1; i<=num_nodes; i++){
				for(int j=1; j<=num_nodes;j++){	
					if(adjMatrix[i][j]==1){
						if(i!=j){
							paths[j]=paths[j]+paths[i];
						}
					}
				}
			}	
			System.out.printf("Number of paths: %d\n", paths[num_nodes]);
			System.out.print("\n\n");
			}
		scan.close();
	}

}
