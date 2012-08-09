class Tag < ActiveRecord::Base
  has_many :contents, :through => :content_tags
  
 scope :alphabetical, order("name")
 scope :for_content, lambda { |content_id| where("content_id = ?", content_id) }
 
end
