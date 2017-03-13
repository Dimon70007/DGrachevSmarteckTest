# DGrachevSmarteckTest
This lib generates Collection of Matrix (cpecified xLength * yLength) and specified colors.
Fourth param is a stateCount to return.
Interface:
class MatrixGenerator:
  method - Collection<Node> generate(Integer ... args)
  args[0]-xLength
  args[1]-yLength
  args[2]-colors
  args[3]-stateCount
  
  return Collection of generated Nodes include rootNode
  
  if args.length==0 generate will be calling with arguments in params.ini
  
  class Node:
    method - int[][] get2DArr()-return inner saved state as matrix
    method - int[] getArr()- return inner saved state as vector
    
  class NodeHelper:
    method - int[] convertMatrixToVector(final int [][] arr)
    method - int[][] convertVetorToMatrix(final int[] arr, final int sizeX)
