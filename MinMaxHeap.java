import java.lang.Math;
import java.util.Arrays;
import java.util.Random;
public class MinMaxHeap {

	private int currentSize;
	private int[] arr;
	private int maxIdx = 0;
	private int minIdx = 1;
	
	public MinMaxHeap(int capacity){//Constructor
		arr = new int[capacity + 1];
		currentSize = 0;
	}
	
	public boolean isFull(){
		return currentSize == arr.length - 1;
	}
	public boolean isEmpty(){
		return currentSize == 0;
	}
	
	public void insert(int x){
		if (isEmpty()){
			arr[1] = x;
			currentSize++;
		}
		else{
			arr[++currentSize] = x;
			percolateUp(currentSize);
		}
	}//PRE: The heap is not full
	public int min(){
		
		return arr[1]; //return root
	}//PRE: The heap is not empty
	public int max(){
		if(currentSize==1){
			maxIdx = 1;
			return arr[1];
		}
		else if(currentSize==2){	//root and one child (already sorted from insert)
			maxIdx=2;
			return arr[2];
		}
		else{
			if (arr[2] < arr[3]) maxIdx = 3;
			else if (arr[3] < arr[2]) maxIdx = 2;
			
			return Math.max(arr[2], arr[3]); //max of 2 children of root.
		}
	}//PRE: The heap is not empty
	public int deleteMin(){
		int tmp = min();//assign min to tmp
		if(currentSize==1){	//no need to percolate
			currentSize--;
			return tmp;
		}
		
		arr[minIdx] = arr[currentSize--];//place hole at arr[1]
		percolateDown(minIdx);
		return tmp;
	}//PRE: The heap is not empty
	public int deleteMax(){
		int tmp = max();
		if(currentSize==1){
			currentSize--;
			return tmp;
		}
		else if(currentSize==2){
			currentSize--;
			return tmp;
		}
		
		arr[maxIdx] = arr[currentSize--];
		percolateDown(maxIdx);
		return tmp;
		
	}//PRE: The heap is not empty
	
	private void switchData(int x, int y){	//switch data at x with data at y
		int tmp = arr[x];
		arr[x]=arr[y];
		arr[y]=tmp;
	}
	
	private boolean evenLevel(int i){	//return true if on evenLevel
		if (log2(i)%2==0){
			return true;
		}
		else return false;
	}
	private int log2(int num){	//log base 2 of num
		return (int) (Math.log(num)/Math.log(2));
	}
	
	private int leftChild(int i){
		return 2*i;
	}
	private int rightChild(int i){
		return ((2*i)+1);
	}
	private int parent(int i){	//return idx of parent
		return i/2;
	}
	private int grandparent(int i){	//return idx of gp
		return i/4;
	}
	
	private int minChild(int i){ //returns idx of min child
		int minChildIdx=0;
		if (arr[leftChild(i)]<arr[rightChild(i)]) minChildIdx=leftChild(i);
		else minChildIdx=rightChild(i);
		return minChildIdx;
	}
	private int maxChild(int i){
		int maxChildIdx=0;
		if (arr[leftChild(i)]>arr[rightChild(i)]) maxChildIdx=leftChild(i);
		else maxChildIdx=rightChild(i);
		return maxChildIdx;
	}
	
	private int minChildIdx(int i){	//return idx of min of children and grandchildren
		int minChildIdx=leftChild(i);	//we know left child exists
		int minAmt=arr[minChildIdx];
		
		if(rightChild(i)<=currentSize){ 
			if(arr[rightChild(i)]<minAmt){
				minAmt=arr[rightChild(i)];
				minChildIdx = rightChild(i);
			}
		}
		else return minChildIdx;
		
		if(leftChild(leftChild(i))<=currentSize){  
			if(arr[leftChild(leftChild(i))]<minAmt){
				minAmt=arr[leftChild(leftChild(i))];
				minChildIdx=leftChild(leftChild(i));
			}
		}
		else return minChildIdx;	//if no leftmost grandchild
		
		if(rightChild(leftChild(i))<=currentSize) {
			if(arr[rightChild(leftChild(i))]<minAmt){
				minAmt=arr[rightChild(leftChild(i))];
				minChildIdx=rightChild(leftChild(i));
			}
		}
		else return minChildIdx; //if no left-right grandchild
		
		if(leftChild(rightChild(i))<=currentSize){ 
			if(arr[leftChild(rightChild(i))]<minAmt){
				minAmt=arr[leftChild(rightChild(i))];
				minChildIdx=leftChild(rightChild(i));
			}
		}
		else return minChildIdx; //if no right-left grandchild
		
		if(rightChild(rightChild(i))<=currentSize){
			if(arr[rightChild(rightChild(i))]<minAmt){
				minAmt=arr[rightChild(rightChild(i))];
				minChildIdx=rightChild(rightChild(i));
			}
		}
		else return minChildIdx; //if no rightmost grandchild
		
		return minChildIdx;
	}
	private int maxChildIdx(int i){	//return idx of max of children and grandchildren
		int maxChildIdx=leftChild(i);	//we know left child exists
		int maxAmt=arr[maxChildIdx];
		
		if(rightChild(i)<=currentSize){ 
			if(arr[rightChild(i)]>maxAmt){
				maxAmt=arr[rightChild(i)];
				maxChildIdx = rightChild(i);
			}
		}
		else return maxChildIdx;
		
		if(leftChild(leftChild(i))<=currentSize){ 
			if(arr[leftChild(leftChild(i))]>maxAmt){
				maxAmt=arr[leftChild(leftChild(i))];
				maxChildIdx=leftChild(leftChild(i));
			}
		}
		else return maxChildIdx; //if no leftmost grandchild
		
		if(rightChild(leftChild(i))<=currentSize){ 
			if(arr[rightChild(leftChild(i))]>maxAmt){
				maxAmt=arr[rightChild(leftChild(i))];
				maxChildIdx=rightChild(leftChild(i));
			}
		}
		else return maxChildIdx; //if no left-right grandchild
		
		if(leftChild(rightChild(i))<=currentSize){ 
			if(arr[leftChild(rightChild(i))]>maxAmt){
				maxAmt=arr[leftChild(rightChild(i))];
				maxChildIdx=leftChild(rightChild(i));
			}
		}
		else return maxChildIdx; //if no right-left grandchild
		
		if(rightChild(rightChild(i))<=currentSize){ 
			if(arr[rightChild(rightChild(i))]>maxAmt){
				maxAmt=arr[rightChild(rightChild(i))];
				maxChildIdx=rightChild(rightChild(i));
			}
		}
		else return maxChildIdx; //if no rightmost grandchild
		
		return maxChildIdx;
	}
	
	private void percolateDown(int node){
		if(leftChild(node)>currentSize) return;	//no kids
		if (evenLevel(node)) percolateDownEven(node);
		else percolateDownOdd(node);
	}

	private void percolateDownEven(int node){
		if (leftChild(node)<=currentSize){ //has children
			
			int minChildIdx=minChildIdx(node);
			boolean smallestChild = false;
			if(minChildIdx==minChild(node)) smallestChild=true;
		
			if(!smallestChild){ //a grandchild is smallest
				if (arr[minChildIdx]<arr[node]){
					switchData(minChildIdx,node);
					if(arr[minChildIdx]>arr[parent(minChildIdx)]){
						switchData(minChildIdx,parent(minChildIdx));
					}
					percolateDownEven(minChildIdx);
				}
			}
			else{	//a child is smallest
				if(arr[minChildIdx]<arr[node]){
					switchData(minChildIdx,node);
				}
				return;
			}
		}
	}
	private void percolateDownOdd(int node){
		if (leftChild(node)<=currentSize){ //has children
		
			int maxChildIdx=maxChildIdx(node);
			boolean smallestChild = false;
			if(maxChildIdx==maxChild(node)) smallestChild=true;
		
			if(!smallestChild){ //a grandchild is smallest
				if (arr[maxChildIdx]>arr[node]){
					switchData(maxChildIdx,node);
					if(arr[maxChildIdx]<arr[parent(maxChildIdx)]){
						switchData(maxChildIdx,parent(maxChildIdx));
					}
					percolateDownOdd(maxChildIdx);
				}
			}
			else{	//a child is smallest
				if(arr[maxChildIdx]>arr[node]){
					switchData(maxChildIdx,node);
				}
				return;
			}
		}
	}
	
	private void percolateUpEven(int node){
		if(node<=1) return;//root
		if((parent(node)<=1)) return;//doesn't have gp (Would not get here if smaller than root)
		if(arr[node]<arr[grandparent(node)]){
			switchData(node,grandparent(node));
			percolateUpEven(grandparent(node));
		}
	}
	private void percolateUpOdd(int node){
		if(node<=1) return;//root
		if((parent(node)<=1)) return;//doesn't have gp (Would not get here if smaller than root)
		if(arr[node]>arr[grandparent(node)]){
			switchData(node,grandparent(node));
			percolateUpOdd(grandparent(node));
		}
	}
	private void percolateUp(int node){
	if (node==1) return;	//root
	if(evenLevel(node)){
		if(arr[node] < arr[parent(node)]){
			percolateUpEven(node);//still even
		}
		else{
			switchData(node,parent(node));
			node = parent(node);	//move hole
			percolateUpOdd(node);//now odd level
		}
	}
	else{
		if(arr[node] > arr[parent(node)]){
			percolateUpOdd(node);//still odd
		}
		else {
			switchData(node,parent(node));
			node = parent(node);
			percolateUpEven(node);//now on an even level
		}
	}
}

	
	public static void main(String[] args){
		Random rand = new Random();
		int randSize=rand.nextInt(100);
		MinMaxHeap h = new MinMaxHeap(randSize);
		
		int max1=0;
		int max2=0;
		int avgVar=0;
		int count=0, totalcount=0;
		String ostring="";
		
		while(totalcount<1000){
			randSize=rand.nextInt(100000);
			h = new MinMaxHeap(randSize);
			
			
			for(int i = 1; i<=randSize;i++) {
				h.insert(rand.nextInt());
			}
			ostring = Arrays.toString(h.arr);
			for(int i = 1; i<=randSize/2;i++) {
				max1=h.deleteMax();
				max2=h.deleteMax();
				
				if((max1<max2)){
					avgVar+=max2-max1;
					count++;
					i=randSize+1;
				}
			}
			totalcount++;
		}
		System.out.println("count:" + count);
	}
}
