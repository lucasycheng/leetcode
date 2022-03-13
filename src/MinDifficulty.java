class MinDifficulty {
    /*https://leetcode.com/problems/minimum-difficulty-of-a-job-schedule/submissions/
     * 
     * input 
     * [11,111,22,222,33,333,44,444]
		6

memo[][]
11		111		111		222		222		333		333		444	
null	122		122		233		null	null	null	null	
null	null	144		344		266		null	null	null	
null	null	null	366		366		477		null	null	
null	null	null	null	399		699		521		null	
null	null	null	null	null	null	null	843	
    */
    public int minDifficulty(int[] jobDifficulty, int d) {
        if(jobDifficulty.length<d) return -1;
        Integer[][] memo = new Integer[d][jobDifficulty.length]; 
        // memo[i][j] = min diff for ith day, work end on j
        //initialize
        memo[0][0] = jobDifficulty[0];
        for(int i = 1 ; i<jobDifficulty.length;i++)memo[0][i] = Math.max(memo[0][i-1],jobDifficulty[i]);
        
//        helper(memo, jobDifficulty, d-1, jobDifficulty.length-1);
//        for(int i = 0 ; i<jobDifficulty.length;i++)System.out.print(jobDifficulty[i]+ "\t");
//        System.out.println();
//        System.out.println();
//        for(int i = 0 ; i < memo.length;i++){
//            for(int j = 0 ; j< memo[0].length; j++){
//                System.out.print(memo[i][j]+ "\t");
//            }
//             System.out.println();
//        }
        return helper(memo, jobDifficulty, d-1, jobDifficulty.length-1);
    }
    public int helper(Integer[][] memo,int[] diff, int day, int job){
        if(day>job)return -1;
        if(memo[day][job]!=null) return memo[day][job];
        if(day == job) memo[day][job]=helper(memo, diff, day-1, job-1)+diff[job];
        else {
            /*
            in order to finish job on day,
                finish job-1 on day-1;
                finish job-1 on day - included on last
                finish job-2 on day - included on last
                finish job-2 on day-1 (included in first)
                finish job-3 on day,
            */
            int result= Integer.MAX_VALUE;
            int curmax = diff[job];
            for(int i =job ;i>=day;i--){
                curmax = Math.max(curmax, diff[i]);
                result = Math.min(result, helper(memo,diff,day-1,i-1) + curmax);
            }
            memo[day][job] = result;
        }
        return memo[day][job];
    }
}
