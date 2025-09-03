/**
 * Provide the solution to LeetCode 1929 here:
 * https://leetcode.com/problems/concatenation-of-array
 */
export function getConcatenation(nums: number[]): number[] {
  // Create a new array with double the length
  const result: number[] = new Array(nums.length * 2);
  
  // Copy the original array twice
  for (let i = 0; i < nums.length; i++) {
    result[i] = nums[i]; // First copy
    result[i + nums.length] = nums[i]; // Second copy
  }
  
  return result;
}

/**
 * Provide the solution to LeetCode 2942 here:
 * https://leetcode.com/problems/find-words-containing-character/
 */
export function findWordsContaining(words: string[], x: string): number[] {
  const result: number[] = [];
  
  // Check each word for the character
  for (let i = 0; i < words.length; i++) {
    if (words[i].indexOf(x) !== -1) { // Character found in word
      result.push(i);
    }
  }
  
  return result;
}
